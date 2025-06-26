package nl.hu.serious_game.application;

import nl.hu.serious_game.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/// Provides LevelTemplates for seeding the database.
/// Also used for verifying these levels in unit tests.
@Component
public class SeedLevelSource {
    private final Logger logger = LoggerFactory.getLogger(SeedLevelSource.class);

    public LevelTemplate createLevel1() {
        logger.info("Creating level 1...");
        // Create objective
        Objective objective = new Objective(1, 20);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        LevelHouse house1 = new LevelHouse(dayProfile, new HouseOptions());
        LevelHouse house2 = new LevelHouse(dayProfile, new HouseOptions());

        // Create a single transformer
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2), 4);

        LevelTemplate level = new LevelTemplate(1, Season.SUMMER, 10, 15, objective, List.of(transformer), new Cost(5, 10, 0.5f), LevelType.GLOBAL, null);

        logger.info("Level 1 created! {}", level);

        return level;
    }

    public LevelTemplate createLevel2() {
        logger.info("Creating level 2...");
        // Create objective
        Objective objective = new Objective(2, 50);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        LevelHouse house1 = new LevelHouse(dayProfile, new HouseOptions(true, false));
        LevelHouse house2 = new LevelHouse(dayProfile, new HouseOptions(false, true));
        LevelHouse house3 = new LevelHouse(dayProfile, new HouseOptions(false, false));

        // Create a single transformer
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2, house3), 4);

        LevelTemplate level = new LevelTemplate(2, Season.SUMMER, 8, 15, objective, List.of(transformer), new Cost(5, 10, 0.5f), LevelType.GLOBAL, null);

        logger.info("Level 2 created! {}", level);

        return level;
    }

    public LevelTemplate createLevel3() {
        logger.info("Creating level 3...");
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
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2, house3, house4), 4);

        LevelTemplate level = new LevelTemplate(3, Season.SUMMER, 11, 19, objective, List.of(transformer), new Cost(5, 10, 0.5f), LevelType.GLOBAL, null);

        logger.info("Level 3 created! {}", level);

        return level;
    }

    public LevelTemplate createLevel4() {
        logger.info("Creating level 4...");
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
        LevelTransformer transformer = new LevelTransformer(new Congestion(false, 0f), List.of(house1, house2, house3, house4, house5), 4);

        LevelTemplate level = new LevelTemplate(4, Season.SUMMER, 10, 18, objective, List.of(transformer), new Cost(5, 10, 0.5f), LevelType.GLOBAL, null);

        logger.info("Level 4 created! {}", level);

        return level;
    }
}
