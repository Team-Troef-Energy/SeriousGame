package nl.hu.serious_game.domain;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class GameLevelTest {
    @Test
    @DisplayName("Test for when transformers is empty")
    public void emptyTransformersTest() {
        Objective objective = new Objective(5, 5);
        assertThrows(IllegalArgumentException.class, () -> new GameLevel(new LevelTemplate(1, Season.SUMMER, 0, 0, objective, new ArrayList<>(), new Cost(5, 10, 0.5f), LevelType.GLOBAL, null), new ArrayList<>()));
    }
}
