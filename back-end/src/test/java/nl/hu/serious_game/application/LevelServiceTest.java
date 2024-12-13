package nl.hu.serious_game.application;

import nl.hu.serious_game.Runner;
import nl.hu.serious_game.application.dto.out.LevelDTO;
import nl.hu.serious_game.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LevelServiceTest {

    private LevelService levelService;

    @BeforeEach
    public void setUp() {
        Runner runner = mock(Runner.class);
        // Instead of creating a level, we mock one
        when(runner.getLevel1()).thenReturn(new Level(Season.SUMMER, 8, 18, new Objective(2, 5), List.of(new Transformer(1, List.of(), 10))));
        this.levelService = new LevelService(runner);
    }

    @Test
    @DisplayName("Template Test")
    public void TemplateTest() {
        assertTrue(true);
    }

    /*
    * Start level service tests
    * All tests for the startLevel method
    */
    @Test
    @DisplayName("Starting level 1 provides a DTO with valid base information")
    public void startLevelTest() {
        // When
        LevelDTO levelDTO = this.levelService.startLevel(1);

        // Then
        assertAll(
                () -> assertNotNull(levelDTO, "LevelDTO should not be null"),
                () -> assertNotNull(levelDTO.hours(), "Hours should not be null"),
                () -> assertTrue(levelDTO.hours().size() > 0, "Hours should not be empty"),
                () -> assertNotNull(levelDTO.season(), "Season should not be null"),
                () -> assertTrue(levelDTO.startTime() >= 0, "Start time should be non-negative"),
                () -> assertTrue(levelDTO.endTime() >= levelDTO.startTime(), "End time should be greater than or equal to start time"),
                () -> assertNotNull(levelDTO.objective(), "Objective should not be null")
        );
    }

    @Test
    @DisplayName("Starting level 1 provides a DTO where the startTime is 8 and the endTime is 18")
    public void startLevelWithCorrectTimesTest() {
        // When
        LevelDTO levelDTO = this.levelService.startLevel(1);

        // Then
        assertAll(
                () -> assertEquals(8, levelDTO.startTime(), "Start time should be 8"),
                () -> assertEquals(18, levelDTO.endTime(), "End time should be 18")
        );
    }
}
