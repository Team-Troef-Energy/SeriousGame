package nl.hu.serious_game.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.serious_game.domain.exceptions.DoesNotExistException;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor // for unit tests
public class GameTransformer implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Setter // being set by LevelTransformer's constructor.
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LevelTransformer template;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Setter // being set by GameLevel's constructor.
    private GameLevel level;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transformer")
    private List<GameHouse> houses;

    @OneToOne(cascade = CascadeType.ALL)
    private Battery battery;
    private Current excessCurrent;

    public GameTransformer(LevelTransformer template, List<GameHouse> houses, int batteries) {
        this.template = template;
        this.houses = houses;
        this.battery = new Battery(batteries);

        for (var house : houses) {
            house.setTransformer(this);
        }
    }

    public Current getCalculatedLeftoverCurrentAtHour(int hour) {
        float demand = 0;
        float production = 0;
        for (GameHouse house : houses) {
            Current current = house.getCurrentAtHour(hour);
            if (current.getDirection() == Direction.DEMAND) {
                demand += current.getAmount();
            } else if (current.getDirection() == Direction.PRODUCTION) {
                production += current.getAmount();
            } else {
                throw new RuntimeException("unexpected direction: " + current.getDirection());
            }
        }
        float total = Math.abs(demand - production);
        Direction direction;
        if (demand > production) {
            direction = Direction.DEMAND;
        } else {
            direction = Direction.PRODUCTION;
        }

        Current current = battery.chargeOrDischarge(new Current(total, direction));

        if (this.template.getCongestion().isHasCongestion() && current.getAmount() > this.template.getCongestion().getMaxCurrent()) {
            excessCurrent = new Current(current.getAmount() - this.template.getCongestion().getMaxCurrent(), direction);
            current = new Current(this.template.getCongestion().getMaxCurrent(), direction);
        } else {
            excessCurrent = new Current(0f, direction);
        }
        return current;
    }

    public void distributePowerCostAtHour(int hour) {
        float totalDemand = 0;
        float totalProduction = 0;

        // Calculate total demand and total production per hour for all houses
        for (GameHouse house : houses) {
            if (house.getCurrentAtHour(hour).getDirection() == Direction.DEMAND) {
                totalDemand += house.getCurrentAtHour(hour).getAmount();
            } else if (house.getCurrentAtHour(hour).getDirection() == Direction.PRODUCTION) {
                totalProduction += house.getCurrentAtHour(hour).getAmount();
            }
        }

        // Distribute the net production proportionally among the houses with net demand to make it fair
        float remainingProduction = totalProduction;
        for (GameHouse house : houses) {
            float netDemand = house.getTotalConsumptionAtHour(hour) - house.getSolarPanelConsumptionAtHour(hour);
            if (netDemand > 0 && remainingProduction > 0) {
                float share = (netDemand / totalDemand) * totalProduction;
                netDemand -= share;
                remainingProduction -= share;
            }
            float powerCost = netDemand * house.getTemplate().getDayProfile().getValueFromColumnAtHour(hour, "PowerCost");
            house.setPowerCost(Math.max(powerCost, 0)); // Ensure powerCost is not negative
            house.setTotalPowerCost(house.getTotalPowerCost() + house.getPowerCost()); // Accumulate total power cost as well
        }
    }

    void setHouseSolarPanels(long houseId, int solarPanels) {
        houses.stream().filter(house -> house.getId() == houseId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setTotalSolarPanels(solarPanels);
    }

    void setHouseBattery(long houseId, int batteries) {
        houses.stream().filter(house -> house.getId() == houseId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setBattery(batteries);
    }

    void setBattery(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of batteries");
        }
        if (amount > this.template.getMaxBatteryCount()) {
            throw new IllegalArgumentException("Cannot exceed the maximum battery count of " + this.template.getMaxBatteryCount());
        }
        this.battery = new Battery(amount);
    }

    @Override
    public GameTransformer clone() {
        try {
            GameTransformer clone = (GameTransformer) super.clone();
            clone.houses = new ArrayList<>();
            for (GameHouse house : this.houses) {
                clone.houses.add(house.clone());
            }
            clone.battery = this.battery.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed");
        }
    }

}
