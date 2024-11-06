package nl.hu.serious_game.domain;

import java.util.List;

public class Transformer {
    private float powerBalance;
    Boolean hasCongestion;
    private float capacity;
    private List<House> houses;
    private Battery batteries;

    public Transformer(List<House> houses, float capacity, int batteries, Boolean hasCongestion) {
        this.houses = houses;
        this.capacity = capacity;
        this.batteries = new Battery(batteries);
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

        Electricity electricity = batteries.use(new Electricity(total, direction));

        if (!hasCongestion) {
            return electricity;
        } else {
            return new Electricity(Math.min(electricity.amount(), this.capacity), direction);
        }
    }

    public List<House> getHouses() {
        return houses;
    }
}
