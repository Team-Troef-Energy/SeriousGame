package nl.hu.serious_game.domain;

import nl.hu.serious_game.domain.exceptions.DoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class Transformer implements Cloneable {
    private final int id;
    private float powerBalance;
    Boolean hasCongestion;
    private float capacity;
    private List<House> houses;
    private Battery battery;

    public Transformer(int id, List<House> houses, float capacity, int batteries, Boolean hasCongestion) {
        this.id = id;
        this.houses = houses;
        this.capacity = capacity;
        this.battery = new Battery(batteries);
        this.hasCongestion = hasCongestion;
    }

    Electricity getLeftoverCurrent(int hour) {
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

        if (!hasCongestion) {
            return electricity;
        } else {
            return new Electricity(Math.min(electricity.amount(), this.capacity), direction);
        }
    }

    public void setHouseSolarPanels(int houseId, int solarPanels) {
        houses.stream().filter(house -> house.getId() == houseId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setTotalSolarPanels(solarPanels);
    }

    public void setHouseBattery(int houseId, int batteries) {
        houses.stream().filter(house -> house.getId() == houseId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setBattery(batteries);
    }

    public void setBattery(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of batteries");
        }
        this.battery = new Battery(amount);
    }

    public List<House> getHouses() {
        return houses;
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
