package nl.hu.serious_game.domain;

import java.util.ArrayList;

public class Transformer {
    private float currentBalance;
    Boolean hasCongestion;
    private float capacity;
    private ArrayList<House> houses;
    private ArrayList<Windmill> windmills;
    private Battery batteries;

    Transformer(ArrayList<House> houses, ArrayList<Windmill> windmills, float capacity, int batteries, Boolean hasCongestion) {
        this.houses = houses;
        this.windmills = windmills;
        this.capacity = capacity;
        this.batteries = new Battery(batteries);
        this.hasCongestion = hasCongestion;
    }

    Electricity getLevtoverCurrent(int hour) {
        float demand = 0;
        float production = 0;
        for (House house : houses) {
            Electricity current = house.getCurrent();
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
}
