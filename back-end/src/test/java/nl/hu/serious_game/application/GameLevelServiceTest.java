package nl.hu.serious_game.application;

import nl.hu.serious_game.application.dto.out.GameLevelDTO;
import nl.hu.serious_game.data.GameLevelRepository;
import nl.hu.serious_game.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameLevelServiceTest {

    private GameLevelService gameLevelService;

    @BeforeEach
    public void setUp() {
        GameLevelRepository gameLevelRepository = mock(GameLevelRepository.class);
        // Instead of creating a level, we mock one
        when(gameLevelRepository.getGameLevelById(1L)).thenReturn(new GameLevel(
                new LevelTemplate(Season.SUMMER, 8, 18, new Objective(2, 5), List.of()),
                List.of(),
                new Cost(0, 0)
        ));
        when(gameLevelRepository.getLevelCount()).thenReturn(1);
        this.gameLevelService = new GameLevelService(gameLevelRepository);
    }

    /*
    * Start level service tests
    * All tests for the startLevel method
    */
    @Test
    @DisplayName("Starting level 1 provides a DTO with valid base information")
    public void startLevelTest() {
        // When
        GameLevelDTO gameLevelDTO = this.gameLevelService.startLevel(1);
        // Then
        assertAll(
                () -> assertNotNull(gameLevelDTO, "LevelDTO should not be null"),
                () -> assertNotNull(gameLevelDTO.hours(), "Hours should not be null"),
                () -> assertTrue(gameLevelDTO.hours().size() > 0, "Hours should not be empty"),
                () -> assertNotNull(gameLevelDTO.season(), "Season should not be null"),
                () -> assertTrue(gameLevelDTO.startTime() >= 0, "Start time should be non-negative"),
                () -> assertTrue(gameLevelDTO.endTime() >= gameLevelDTO.startTime(), "End time should be greater than or equal to start time"),
                () -> assertNotNull(gameLevelDTO.objective(), "Objective should not be null")
        );
    }

    @Test
    @DisplayName("Starting level 1 provides a DTO where the startTime is 8 and the endTime is 18")
    public void startLevelWithCorrectTimesTest() {
        // When
        GameLevelDTO gameLevelDTO = this.gameLevelService.startLevel(1);

        // Then
        assertAll(
                () -> assertEquals(8, gameLevelDTO.startTime(), "Start time should be 8"),
                () -> assertEquals(18, gameLevelDTO.endTime(), "End time should be 18")
        );
    }
}
