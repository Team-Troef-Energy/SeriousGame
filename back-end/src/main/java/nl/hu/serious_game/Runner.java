package nl.hu.serious_game;

import nl.hu.serious_game.domain.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private Level level1;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Runner started!");
        this.createLevel1();
    }

    private void createLevel1() {
        System.out.println("Creating level 1...");
        // Create objective
        Objective objective = new Objective(2, 5);

        // Create a DayProfile
        DayProfile dayProfile = new DayProfile(Season.SUMMER);

        // Create a house
        House house = new House(1, 4, dayProfile);
        House house2 = new House(2, 8, dayProfile);

        // Create a single transformer
        Transformer transformer = new Transformer(List.of(house, house2), 10, 1, false);

        this.level1 = new Level(Season.SUMMER, 12, 15, objective, List.of(transformer));

        System.out.println("Level 1 created!");
        System.out.println(level1);
    }

    public Level getLevel1() {
        return level1;
    }
}
