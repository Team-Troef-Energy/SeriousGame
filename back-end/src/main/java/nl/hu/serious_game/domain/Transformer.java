package nl.hu.serious_game.domain;

import java.util.ArrayList;
import java.util.List;

import nl.hu.serious_game.domain.exceptions.DoesNotExistException;

public class Transformer implements Cloneable {
    private final int id;
    private float powerBalance;
    private Congestion congestion;
    private List<House> houses;
    private Battery battery;
    private Current excessCurrent;
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

    public Current getCalculatedLeftoverCurrentAtHour(int hour) {
        float demand = 0;
        float production = 0;
        for (House house : houses) {
            Current current = house.getCurrentAtHour(hour);
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

        Current current = battery.chargeOrDischarge(new Current(total, direction));

        if (congestion.hasCongestion() && current.amount() > congestion.maxCurrent()) {
            excessCurrent = new Current(current.amount() - congestion.maxCurrent(), direction);
            current = new Current(congestion.maxCurrent(), direction);
        } else {
            excessCurrent = new Current(0f, direction);
        }
        return current;
    }

    public void distributePowerCostAtHour(int hour) {
        float totalDemand = 0;
        float totalProduction = 0;

        // Calculate total demand and total production per hour for all houses
        for (House house : houses) {
            if (house.getCurrentAtHour(hour).direction() == Direction.DEMAND) {
                totalDemand += house.getCurrentAtHour(hour).amount();
            } else if (house.getCurrentAtHour(hour).direction() == Direction.PRODUCTION) {
                totalProduction += house.getCurrentAtHour(hour).amount();
            }
        }

        // Distribute the net production proportionally among the houses with net demand to make it fair
        float remainingProduction = totalProduction;
        for (House house : houses) {
            float netDemand = house.getTotalConsumptionAtHour(hour) - house.getSolarPanelConsumptionAtHour(hour);
            if (netDemand > 0 && remainingProduction > 0) {
                float share = (netDemand / totalDemand) * totalProduction;
                netDemand -= share;
                remainingProduction -= share;
            }
            float powerCost = netDemand * house.getDayProfile().getValueFromColumnAtHour(hour, "PowerCost");
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

    public Current getExcessCurrent() {
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
