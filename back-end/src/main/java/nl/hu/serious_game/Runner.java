package nl.hu.serious_game;

import nl.hu.serious_game.data.LevelTemplateRepository;
import nl.hu.serious_game.data.LevelTemplateRepository;
import nl.hu.serious_game.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private final LevelTemplateRepository levelTemplateRepository;

    @Autowired
    public Runner(LevelTemplateRepository levelTemplateRepository) {
        this.levelTemplateRepository = levelTemplateRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Runner started!");
        if (levelTemplateRepository.getLevelCount() == 0) {
            levelTemplateRepository.save(createLevel1());
            levelTemplateRepository.save(createLevel2());
            levelTemplateRepository.save(createLevel3());
            levelTemplateRepository.save(createLevel4());
        }
    }

    public LevelTemplate createLevel1() {
        System.out.println("Creating level 1...");
        // Create objective
        Objective objective = new Objective(1, 20);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        LevelHouse house1 = new LevelHouse(dayProfile, new HouseOptions());
        LevelHouse house2 = new LevelHouse(dayProfile, new HouseOptions());

        // Create a single transformer
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2), 0);

        LevelTemplate level = new LevelTemplate(1, Season.SUMMER, 10, 15, objective, List.of(transformer));

        System.out.println("Level 1 created!");
        System.out.println(level);

        return level;
    }

    public LevelTemplate createLevel2() {
        System.out.println("Creating level 2...");
        // Create objective
        Objective objective = new Objective(2, 50);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        LevelHouse house1 = new LevelHouse(dayProfile, new HouseOptions(true, false));
        LevelHouse house2 = new LevelHouse(dayProfile, new HouseOptions(false, true));
        LevelHouse house3 = new LevelHouse(dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2, house3), 0);

        LevelTemplate level = new LevelTemplate(2, Season.SUMMER, 8, 15, objective, List.of(transformer));

        System.out.println("Level 2 created!");
        System.out.println(level);

        return level;
    }

    public LevelTemplate createLevel3() {
        System.out.println("Creating level 3...");
        // Create objective
        Objective objective = new Objective(4, 150);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        LevelHouse house1 = new LevelHouse(dayProfile, new HouseOptions(false, false));
        LevelHouse house2 = new LevelHouse(dayProfile, new HouseOptions(false, true));
        LevelHouse house3 = new LevelHouse(dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
        LevelHouse house4 = new LevelHouse(dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2, house3, house4), 0);

        LevelTemplate level = new LevelTemplate(3, Season.SUMMER, 11, 19, objective, List.of(transformer));

        System.out.println("Level 3 created!");
        System.out.println(level);

        return level;
    }

    public LevelTemplate createLevel4() {
        System.out.println("Creating level 4...");
        // Create objective
        Objective objective = new Objective(5, 100);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create houses
        LevelHouse house1 = new LevelHouse(dayProfile, new HouseOptions(false, true));
        LevelHouse house2 = new LevelHouse(dayProfile, new HouseOptions(true, false));
        LevelHouse house3 = new LevelHouse(dayProfile, new HouseOptions(true, true, new Congestion(true, 0.7f)));
        LevelHouse house4 = new LevelHouse(dayProfile, new HouseOptions(true, true, new Congestion(true, 0.5f)));
        LevelHouse house5 = new LevelHouse(dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2, house3, house4, house5), 0);

        LevelTemplate level = new LevelTemplate(4, Season.SUMMER, 10, 18, objective, List.of(transformer));

        System.out.println("Level 4 created!");
        System.out.println(level);

        return level;
    }
}
