package nl.hu.serious_game;

import nl.hu.serious_game.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private List<Level> levels;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Runner started!");
        this.createLevels();
    }

    private void createLevels() {
        this.levels = List.of(createLevel1(), createLevel2());
    }

    private Level createLevel1() {
        System.out.println("Creating level 1...");
        // Create objective
        Objective objective = new Objective(2, 5);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house = new House(1, 4, dayProfile, new HouseOptions());
        House house2 = new House(2, 8, dayProfile, new HouseOptions());

        // Create a single transformer
        Transformer transformer = new Transformer(1, List.of(house, house2), 1);

        Level level = new Level(Season.SUMMER, 12, 15, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 1 created!");
        System.out.println(level);

        return level;
    }

    private Level createLevel2() {
        System.out.println("Creating level 2...");
        // Create objective
        Objective objective = new Objective(3, 5);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house = new House(1, 4, dayProfile, new HouseOptions(true, false));
        House house2 = new House(2, 8, dayProfile, new HouseOptions(false, true));
        House house3 = new House(3, 8, dayProfile, new HouseOptions(true, true));

        // Create a single transformer
        Transformer transformer = new Transformer(1, List.of(house, house2, house3), 1);

        Level level = new Level(Season.SUMMER, 12, 15, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 2 created!");
        System.out.println(level);

        return level;
    }

    public Level getLevel(int levelNumber) {
        return levels.get(levelNumber - 1);
    }

    public int getTotalLevels() {
        return levels.size();
    }
}
