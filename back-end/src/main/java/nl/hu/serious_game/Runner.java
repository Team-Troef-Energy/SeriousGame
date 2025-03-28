package nl.hu.serious_game;

import nl.hu.serious_game.data.GameLevelRepository;
import nl.hu.serious_game.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private final GameLevelRepository gameLevelRepository;

    @Autowired
    public Runner(GameLevelRepository gameLevelRepository) {
        this.gameLevelRepository = gameLevelRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Runner started!");
        if (gameLevelRepository.getLevelCount() == 0) {
            gameLevelRepository.save(createLevel1());
            gameLevelRepository.save(createLevel2());
            gameLevelRepository.save(createLevel3());
            gameLevelRepository.save(createLevel4());
        }
    }

    public GameLevel createLevel1() {
        System.out.println("Creating level 1...");
        // Create objective
        Objective objective = new Objective(1, 20);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        GameHouse house1 = new GameHouse(0, dayProfile, new HouseOptions());
        GameHouse house2 = new GameHouse(0, dayProfile, new HouseOptions());

        // Create a single transformer
        GameTransformer transformer = new GameTransformer(List.of(house1, house2), 0);

        GameLevel level = new GameLevel(Season.SUMMER, 10, 15, objective, List.of(transformer), new Cost(5, 10));
        level.setId(1L);

        System.out.println("Level 1 created!");
        System.out.println(level);

        return level;
    }

    public GameLevel createLevel2() {
        System.out.println("Creating level 2...");
        // Create objective
        Objective objective = new Objective(2, 50);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        GameHouse house1 = new GameHouse(0, dayProfile, new HouseOptions(true, false));
        GameHouse house2 = new GameHouse(0, dayProfile, new HouseOptions(false, true));
        GameHouse house3 = new GameHouse(0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        GameTransformer transformer = new GameTransformer(List.of(house1, house2, house3), 0);

        GameLevel level = new GameLevel(Season.SUMMER, 8, 15, objective, List.of(transformer), new Cost(5, 10));
        level.setId(2L);

        System.out.println("Level 2 created!");
        System.out.println(level);

        return level;
    }

    public GameLevel createLevel3() {
        System.out.println("Creating level 3...");
        // Create objective
        Objective objective = new Objective(4, 150);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        GameHouse house1 = new GameHouse(0, dayProfile, new HouseOptions(false, false));
        GameHouse house2 = new GameHouse(0, dayProfile, new HouseOptions(false, true));
        GameHouse house3 = new GameHouse(0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
        GameHouse house4 = new GameHouse(0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        GameTransformer transformer = new GameTransformer(List.of(house1, house2, house3, house4), 0);

        GameLevel level = new GameLevel(Season.SUMMER, 11, 19, objective, List.of(transformer), new Cost(5, 10));
        level.setId(3L);

        System.out.println("Level 3 created!");
        System.out.println(level);

        return level;
    }

    public GameLevel createLevel4() {
        System.out.println("Creating level 4...");
        // Create objective
        Objective objective = new Objective(5, 100);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create houses
        GameHouse house1 = new GameHouse(0, dayProfile, new HouseOptions(false, true));
        GameHouse house2 = new GameHouse(0, dayProfile, new HouseOptions(true, false));
        GameHouse house3 = new GameHouse(0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.7f)));
        GameHouse house4 = new GameHouse(0, dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
        GameHouse house5 = new GameHouse(0, dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        GameTransformer transformer = new GameTransformer(List.of(house1, house2, house3, house4, house5), 0);

        GameLevel level = new GameLevel(Season.SUMMER, 10, 18, objective, List.of(transformer), new Cost(5, 10));
        level.setId(4L);

        System.out.println("Level 4 created!");
        System.out.println(level);

        return level;
    }
}
