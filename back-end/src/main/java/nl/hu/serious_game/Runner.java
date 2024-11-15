package nl.hu.serious_game;

import nl.hu.serious_game.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private Level level1;
    private Level level2;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Runner started!");
        this.createLevel1();
        this.createLevel2();
    }

    private void createLevel1() {
        System.out.println("Creating level 1...");
        // Create objective
        Objective objective = new Objective(2, 5);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house = new House(1, 4, dayProfile, new HouseOptions());
        House house2 = new House(2, 8, dayProfile, new HouseOptions());

        // Create a single transformer
        Transformer transformer = new Transformer(List.of(house, house2), 10, 1, false);

        this.level1 = new Level(Season.SUMMER, 12, 15, objective, List.of(transformer));

        System.out.println("Level 1 created!");
        System.out.println(level1);
    }

    private void createLevel2() {
        System.out.println("Creating level 2...");
        // Create objective
        Objective objective = new Objective(3, 5);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house = new House(1, 4, dayProfile, new HouseOptions(true, false));
        House house2 = new House(2, 8, dayProfile, new HouseOptions(false, true));
        House house3 = new House(2, 8, dayProfile, new HouseOptions(true, true));

        // Create a single transformer
        Transformer transformer = new Transformer(List.of(house, house2, house3), 10, 1, false);

        this.level2 = new Level(Season.SUMMER, 12, 15, objective, List.of(transformer));

        System.out.println("Level 2 created!");
        System.out.println(level2);
    }

    public Level getLevel1() {
        return level1;
    }

    public Level getLevel2() {
        return level2;
    }
}
