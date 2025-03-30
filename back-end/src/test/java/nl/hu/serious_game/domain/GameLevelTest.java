package nl.hu.serious_game.domain;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class GameLevelTest {
    @Test
    @DisplayName("Test for when transformers is empty")
    public void emptyTransformersTest() {
        Objective objective = new Objective(5, 5);
        assertThrows(IllegalArgumentException.class, () -> new GameLevel(new LevelTemplate(Season.SUMMER, 0, 0, objective, new ArrayList<>()), new ArrayList<>(), new Cost(5, 10)));
    }
}
