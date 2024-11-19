package nl.hu.serious_game.application;

import nl.hu.serious_game.Runner;
import nl.hu.serious_game.domain.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

    private final Runner runner;

    @Autowired
    public LevelService(Runner runner) {
        this.runner = runner;
    }

    // Returns the level that has been selected to show to the user so they know what to start with.
    public Level startLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return runner.getLevel1();
            case 2:
                return runner.getLevel2();
            default:
                throw new IllegalArgumentException("Invalid level number");
        }
    }
}
