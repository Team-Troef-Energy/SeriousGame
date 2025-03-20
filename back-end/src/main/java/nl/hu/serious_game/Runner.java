package nl.hu.serious_game;

import nl.hu.serious_game.data.LevelRepository;
import nl.hu.serious_game.domain.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private final LevelRepository levelRepository;

    @Autowired
    public Runner(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Runner started!");
        if (levelRepository.getLevelCount() == 0) {
            levelRepository.save(createLevel1());
            levelRepository.save(createLevel2());
            levelRepository.save(createLevel3());
            levelRepository.save(createLevel4());
        }
    }

    public Level createLevel1() {
        System.out.println("Creating level 1...");
        // Create objective
        Objective objective = new Objective(1, 20);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house1 = new House(0, dayProfile, new HouseOptions());
        House house2 = new House(0, dayProfile, new HouseOptions());

        // Create a single transformer
        Transformer transformer = new Transformer(List.of(house1, house2), 0);

        Level level = new Level(Season.SUMMER, 10, 15, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 1 created!");
        System.out.println(level);

        return level;
    }

    public Level createLevel2() {
        System.out.println("Creating level 2...");
        // Create objective
        Objective objective = new Objective(2, 50);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house1 = new House(0, dayProfile, new HouseOptions(true, false));
        House house2 = new House(0, dayProfile, new HouseOptions(false, true));
        House house3 = new House(0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        Transformer transformer = new Transformer(List.of(house1, house2, house3), 0);

        Level level = new Level(Season.SUMMER, 8, 15, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 2 created!");
        System.out.println(level);

        return level;
    }

    public Level createLevel3() {
        System.out.println("Creating level 3...");
        // Create objective
        Objective objective = new Objective(4, 150);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house1 = new House(0, dayProfile, new HouseOptions(false, false));
        House house2 = new House(0, dayProfile, new HouseOptions(false, true));
        House house3 = new House(0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
        House house4 = new House(0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        Transformer transformer = new Transformer(List.of(house1, house2, house3, house4), 0);

        Level level = new Level(Season.SUMMER, 11, 19, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 3 created!");
        System.out.println(level);

        return level;
    }

    public Level createLevel4() {
        System.out.println("Creating level 4...");
        // Create objective
        Objective objective = new Objective(5, 100);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create houses
        House house1 = new House(0, dayProfile, new HouseOptions(false, true));
        House house2 = new House(0, dayProfile, new HouseOptions(true, false));
        House house3 = new House(0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.7f)));
        House house4 = new House(0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
        House house5 = new House(0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        Transformer transformer = new Transformer(List.of(house1, house2, house3, house4, house5), 0);

            Level level = new Level(Season.SUMMER, 10, 18, objective, List.of(transformer), new Cost(5, 10));

        System.out.println("Level 4 created!");
        System.out.println(level);

        return level;
    }
}
