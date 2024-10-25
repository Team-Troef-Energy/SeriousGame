package nl.hu.serious_game.domain;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class ObjectiveTest {
    @Test
    @DisplayName("Test for when maxCo2 is negative")
    public void negativeMaxCo2Test() {
        assertThrows(IllegalArgumentException.class, () -> new Objective(-5, 5));
    }

    @Test
    @DisplayName("Test for when maxCoins is negative")
    public void negativeMaxCoinsTest() {
        assertThrows(IllegalArgumentException.class, () -> new Objective(5, -5));
    }

    @Test
    @DisplayName("Test for when both maxCo2 and maxCoins are negative")
    public void negativeMaxCo2AndMaxCoinsTest() {
        assertThrows(IllegalArgumentException.class, () -> new Objective(-5, -5));
    }

    @Test
    @DisplayName("Test for when both maxCo2 and maxCoins are positive")
    public void positiveMaxCo2AndMaxCoinsTest() {
        Objective objective = new Objective(5, 5);
        assertEquals(objective.getMaxCo2(), 5);
        assertEquals(objective.getMaxCoins(), 5);
    }
}
