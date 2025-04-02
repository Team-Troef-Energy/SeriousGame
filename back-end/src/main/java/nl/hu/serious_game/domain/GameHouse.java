package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor // for unit tests
public class GameHouse implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private LevelHouse template;

    private int totalSolarPanels;

    @OneToOne(cascade = CascadeType.ALL)
    private Battery battery;
    private Current excessCurrent;

    @Setter
    private float powerCost; // Cost in euros
    @Setter
    private float totalPowerCost; // Cost in euros

    public GameHouse(LevelHouse template, int totalSolarPanels) {
        this.template = template;
        this.totalSolarPanels = totalSolarPanels;
    }

    public void addSolarPanel(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of solar panels");
        }

        if (amount > this.template.getHouseOptions().maxSolarPanelCount()) {
            throw new IllegalArgumentException("Cannot exceed the maximum number of solar panels: " + this.template.getHouseOptions().maxSolarPanelCount());
        }
        totalSolarPanels += amount;
    }

    void setTotalSolarPanels(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot set amount of solar panels to negative number");
        }

        if (amount > this.template.getHouseOptions().maxSolarPanelCount()) {
            throw new IllegalArgumentException("Cannot exceed the maximum number of solar panels: " + this.template.getHouseOptions().maxSolarPanelCount());
        }
        this.totalSolarPanels = amount;
    }

    public void removeSolarPanel(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot remove a negative amount of solar panels");
        }
        if (totalSolarPanels < amount) {
            throw new IllegalArgumentException("Cannot remove more solar panels than there are");
        }
        totalSolarPanels -= amount;
    }

    public void setBattery(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of batteries");
        }

        if (amount > this.template.getHouseOptions().maxBatteryCount()) {
            throw new IllegalArgumentException("Cannot exceed the maximum number of batteries: " + this.template.getHouseOptions().maxBatteryCount());
        }
        this.battery = new Battery(amount);
    }

    public float getSolarPanelConsumptionAtHour(int hour) {
        return totalSolarPanels * this.template.getDayProfile().getValueFromColumnAtHour(hour, "SolarPanelProduction");
    }

    public float getBaseConsumptionAtHour(int hour) {
        return this.template.getDayProfile().getValueFromColumnAtHour(hour, "HouseBaseConsumption");
    }

    public float getHeatPumpConsumptionAtHour(int hour) {
        return this.template.getDayProfile().getValueFromColumnAtHour(hour, "HeatPumpConsumption");
    }

    public float getElectricVehicleConsumptionAtHour(int hour) {
        return this.template.getDayProfile().getValueFromColumnAtHour(hour, "ElectricVehicleConsumption");
    }

    // Returns the current for the house at a specific hour
    public Current getCurrentAtHour(int hour) {
        return getCalculatedCurrentAtHour(hour);
        /*
        if (this.hour == null || hour == this.hour + 1) {
            return getCalculatedCurrentAtHour(hour);
        }
        if (hour == this.hour) {
            return this.current;
        }
        throw new IllegalArgumentException("Invalid hour");
         */
    }

    private Current getCalculatedCurrentAtHour(int hour) {
        float production = getSolarPanelConsumptionAtHour(hour);
        float consumption = getTotalConsumptionAtHour(hour);
        float amount;
        Direction direction;
        if (production > consumption) {
            amount = production - consumption;
            direction = Direction.PRODUCTION;
        } else {
            amount = consumption - production;
            direction = Direction.DEMAND;
        }
        Current current = new Current(amount, direction);

        if (battery != null) {
            current = battery.chargeOrDischarge(current);
        }

        if (this.template.getHouseOptions().hasCongestion() && current.getAmount() > this.template.getHouseOptions().maxCurrent()) {
            excessCurrent = new Current(current.getAmount() - this.template.getHouseOptions().maxCurrent(), direction);
            current = new Current(this.template.getHouseOptions().maxCurrent(), direction);
        } else {
            // Direction is not important here.
            excessCurrent = new Current(0f, direction);
        }
        return current;
    }

    public float getTotalConsumptionAtHour(int hour) {
        float total = getBaseConsumptionAtHour(hour);
        total += this.template.getHouseOptions().hasHeatPump() ? getHeatPumpConsumptionAtHour(hour) : 0;
        total += this.template.getHouseOptions().hasElectricVehicle() ? getElectricVehicleConsumptionAtHour(hour) : 0;
        return total;
    }

    @Override
    public GameHouse clone() {
        try {
            GameHouse clone = (GameHouse) super.clone();
            if (battery != null) {
                clone.battery = battery.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed");
        }
    }
}