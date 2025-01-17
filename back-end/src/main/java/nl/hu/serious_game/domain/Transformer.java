package nl.hu.serious_game.domain;

import nl.hu.serious_game.domain.exceptions.DoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class Transformer implements Cloneable {
    private final int id;
    private float powerBalance;
    private Congestion congestion;
    private List<House> houses;
    private Battery battery;
    private Electricity excessCurrent;
    private int maxBatteryCount = 4;

    public Transformer(int id, List<House> houses, int batteries) {
        this.id = id;
        this.houses = houses;
        this.battery = new Battery(batteries);
        this.congestion = new Congestion(false, 0f);
    }

    public Transformer(int id, List<House> houses, int batteries, Congestion congestion) {
        this.id = id;
        this.houses = houses;
        this.battery = new Battery(batteries);
        this.congestion = congestion;
    }

    public Electricity calculateLeftoverCurrent(int hour) {
        float demand = 0;
        float production = 0;
        for (House house : houses) {
            Electricity current = house.current(hour);
            if (current.direction() == Direction.DEMAND) {
                demand += current.amount();
            } else if (current.direction() == Direction.PRODUCTION) {
                production += current.amount();
            } else {
                throw new RuntimeException("unexpected direction: " + current.direction());
            }
        }
        float total = Math.abs(demand - production);
        Direction direction;
        if (demand > production) {
            direction = Direction.DEMAND;
        } else {
            direction = Direction.PRODUCTION;
        }

        Electricity electricity = battery.use(new Electricity(total, direction));

        if (congestion.hasCongestion() && electricity.amount() > congestion.maxCurrent()) {
            excessCurrent = new Electricity(electricity.amount() - congestion.maxCurrent(), direction);
            electricity = new Electricity(congestion.maxCurrent(), direction);
        } else {
            excessCurrent = new Electricity(0f, direction);
        }
        return electricity;
    }

    public void distributePowerCost(int hour) {
        float totalDemand = 0;
        float totalProduction = 0;

        // Calculate total demand and total production per hour for all houses
        for (House house : houses) {
            if (house.current(hour).direction() == Direction.DEMAND) {
                totalDemand += house.current(hour).amount();
            } else if (house.current(hour).direction() == Direction.PRODUCTION) {
                totalProduction += house.current(hour).amount();
            }
        }

        // Distribute the net production proportionally among the houses with net demand to make it fair
        float remainingProduction = totalProduction;
        for (House house : houses) {
            float netDemand = house.getTotalConsumptionOfHour(hour) - house.getSolarPanelOutput(hour);
            if (netDemand > 0 && remainingProduction > 0) {
                float share = (netDemand / totalDemand) * totalProduction;
                netDemand -= share;
                remainingProduction -= share;
            }
            float powerCost = netDemand * house.getDayProfile().getValue(hour, "PowerCost");
            house.setPowerCost(Math.max(powerCost, 0)); // Ensure powerCost is not negative
            house.setTotalPowerCost(house.getTotalPowerCost() + house.getPowerCost()); // Accumulate total power cost as well
        }
    }

    void setHouseSolarPanels(int houseId, int solarPanels) {
        houses.stream().filter(house -> house.getId() == houseId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setTotalSolarPanels(solarPanels);
    }

    void setHouseBattery(int houseId, int batteries) {
        houses.stream().filter(house -> house.getId() == houseId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setBattery(batteries);
    }

    void setBattery(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of batteries");
        }
        if (amount > maxBatteryCount) {
            throw new IllegalArgumentException("Cannot exceed the maximum battery count of " + maxBatteryCount);
        }
        this.battery = new Battery(amount);
    }

    public List<House> getHouses() {
        return houses;
    }

    public Battery getBatteries() {
        return battery;
    }

    public Electricity getExcessCurrent() {
        return excessCurrent;
    }

    public int getMaxBatteryCount() {
        return maxBatteryCount;
    }

    @Override
    public Transformer clone() {
        try {
            Transformer clone = (Transformer) super.clone();
            clone.houses = new ArrayList<>();
            for (House house : this.houses) {
                clone.houses.add(house.clone());
            }
            clone.battery = this.battery.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed");
        }
    }

    public int getId() {
        return id;
    }

    public Congestion getCongestion() {
        return congestion;
    }
}
