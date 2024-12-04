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

    public Electricity getLeftoverCurrent(int hour) {
        float demand = 0;
        float production = 0;
        for (House house : houses) {
            Electricity current = house.getCurrent(hour);
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

    int getId() {
        return id;
    }


}
