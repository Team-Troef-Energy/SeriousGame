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
        this.levels = List.of(createLevel1(), createLevel2(), createLevel3(), createLevel4());
    }

    private Level createLevel1() {
        System.out.println("Creating level 1...");
        // Create objective
        Objective objective = new Objective(1, 20);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house1 = new House(1, 0, dayProfile, new HouseOptions());
        House house2 = new House(2, 0, dayProfile, new HouseOptions());

        // Create a single transformer
        Transformer transformer = new Transformer(1, List.of(house1, house2), 0);

        Level level = new Level(Season.SUMMER, 10, 15, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 1 created!");
        System.out.println(level);

        return level;
    }

    private Level createLevel2() {
        System.out.println("Creating level 2...");
        // Create objective
        Objective objective = new Objective(2, 50);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house1 = new House(1, 0, dayProfile, new HouseOptions(true, false));
        House house2 = new House(2, 0, dayProfile, new HouseOptions(false, true));
        House house3 = new House(3, 0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        Transformer transformer = new Transformer(1, List.of(house1, house2, house3), 0);

        Level level = new Level(Season.SUMMER, 8, 15, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 2 created!");
        System.out.println(level);

        return level;
    }

    private Level createLevel3() {
        System.out.println("Creating level 3...");
        // Create objective
        Objective objective = new Objective(4, 150);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house1 = new House(1, 0, dayProfile, new HouseOptions(false, false));
        House house2 = new House(2, 0, dayProfile, new HouseOptions(false, true));
        House house3 = new House(3, 0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
        House house4 = new House(4, 0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        Transformer transformer = new Transformer(1, List.of(house1, house2, house3, house4), 0);

        Level level = new Level(Season.SUMMER, 11, 19, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 3 created!");
        System.out.println(level);

        return level;
    }

    private Level createLevel4() {
    System.out.println("Creating level 4...");
    // Create objective
    Objective objective = new Objective(5, 100);

    // Create a DayProfile
    DayProfile dayProfile = new DayProfile(Season.SUMMER);

    // Create houses
    House house1 = new House(1, 0, dayProfile, new HouseOptions(false, true));
    House house2 = new House(2, 0, dayProfile, new HouseOptions(true, false));
    House house3 = new House(3, 0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.7f)));
    House house4 = new House(4, 0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
    House house5 = new House(5, 0, dayProfile, new HouseOptions(false, false));

    // Create a single transformer
    Transformer transformer = new Transformer(1, List.of(house1, house2, house3, house4, house5), 0);

        Level level = new Level(Season.SUMMER, 10, 18, objective, List.of(transformer), new Cost(5, 10));

    System.out.println("Level 4 created!");
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
