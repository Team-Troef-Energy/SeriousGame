package nl.hu.serious_game;

import nl.hu.serious_game.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GameLevelInitializationTest {

    private Season season;
    private int startTime;
    private int endTime;
    private Objective objective;
    private List<GameTransformer> transformers;

    @BeforeEach
    public void setUp() {
        // Mock dependencies
        season = Season.SUMMER;
        startTime = 8;
        endTime = 18;
        objective = mock(Objective.class); // Mock Objective

        // Create a mock Transformer with a mock list of Houses
        GameTransformer mockTransformer = mock(GameTransformer.class);
        GameHouse mockHouse = mock(GameHouse.class);
        List<GameHouse> houses = new ArrayList<>();
        houses.add(mockHouse);

        // Set up mock behavior for the Transformer
        when(mockTransformer.getHouses()).thenReturn(houses);

        transformers = new ArrayList<>();
        transformers.add(mockTransformer);
    }

    // Test level initialization separate from the runner
    @Test
    @DisplayName("Test level initialization")
    public void testLevelInitialization() {
        GameLevel level = new GameLevel(new LevelTemplate(1, season, startTime, endTime, objective, null), transformers, new Cost(5, 10));

        assertNotNull(level.getTemplate().getObjective(), "Template objective should be initialized");
        assertEquals(objective, level.getTemplate().getObjective(), "Template objective should match the provided value");

        assertNotNull(level.getTransformers(), "Transformers list should be initialized");
        assertFalse(level.getTransformers().isEmpty(), "Transformers list should not be empty");
        assertEquals(transformers, level.getTransformers(), "Transformers should match the provided list");

        GameTransformer transformer = level.getTransformers().get(0);
        assertNotNull(transformer.getHouses(), "Transformer should have a list of houses initialized");
        assertFalse(transformer.getHouses().isEmpty(), "Transformer should have at least one house");
    }

    // Test level initialization through the runner
    @Autowired
    private Runner runner;

    @Test
    @DisplayName("Test createLevel1 method of runner")
    void testCreateLevel1() {
        LevelTemplate level = runner.createLevel1();

        assertNotNull(level, "Level should not be null");
        assertEquals(Season.SUMMER, level.getSeason(), "Season should be SUMMER");
        assertEquals(10, level.getStartTime(), "Start time should be 12");
        assertEquals(15, level.getEndTime(), "End time should be 15");
        assertEquals(1, level.getObjective().getMaxCo2(), "Max CO2 should be 2");
        assertEquals(20, level.getObjective().getMaxCoins(), "Max coins should be 5");
        assertEquals(1, level.getTransformers().size(), "There should be one transformer");
    }
}