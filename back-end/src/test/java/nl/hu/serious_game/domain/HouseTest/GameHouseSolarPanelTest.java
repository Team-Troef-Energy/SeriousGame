package nl.hu.serious_game.domain.HouseTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import nl.hu.serious_game.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GameHouseSolarPanelTest {
    // If its summer and the time is 12:00, the solar panel should have an output of 0.196428571 kwh
    @Test
    @DisplayName("Summer Test with one solar panel")
    public void SummerTestOneSolarPanel() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);
        assertEquals(0.196428571f, house.getSolarPanelConsumptionAtHour(12));
    }

    // Parameterized test for checking the solar panel output for different seasons and hours
    @ParameterizedTest
    @DisplayName("Season Test with one solar panel")
    @MethodSource("provideSolarPanelSeasonAndTimeExamples")
    public void SeasonTestOneSolarPanel(Season season, int hour, float expectedOutput) {
        DayProfile dayProfile = new DayProfile(season);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);
        assertEquals(expectedOutput, house.getSolarPanelConsumptionAtHour(hour));
    }

    // Source of arguments for the parameterized test, containing: Season, Hour, Expected output
    private static Stream<Arguments> provideSolarPanelSeasonAndTimeExamples() {
        return Stream.of(
                Arguments.of(Season.SUMMER, 12, 0.196428571f),
                Arguments.of(Season.WINTER, 12, 0.05303571417f),
                Arguments.of(Season.SPRING, 12, 0.17482142819f),
                Arguments.of(Season.AUTUMN, 12, 0.10607142834f),
                Arguments.of(Season.SUMMER, 0, 0f),
                Arguments.of(Season.WINTER, 0, 0f),
                Arguments.of(Season.SPRING, 0, 0f),
                Arguments.of(Season.AUTUMN, 0, 0f),
                Arguments.of(Season.SUMMER, 18, 0.017857143f),
                Arguments.of(Season.WINTER, 18, 0.004821429f),
                Arguments.of(Season.SPRING, 18, 0.01589285714f),
                Arguments.of(Season.AUTUMN, 18, 0.009642858f)
        );
    }

    // Test with different solar panel amounts
    @ParameterizedTest
    @DisplayName("Solar Panel Amount Test")
    @MethodSource("provideSolarPanelAmountExamples")
    public void SolarPanelAmountTest(int solarPanelAmount, int hour, float expectedOutput) {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), solarPanelAmount);
        assertEquals(expectedOutput, house.getSolarPanelConsumptionAtHour(hour));
    }

    // Source of arguments for the parameterized test, containing: Solar panel amount, Hour, Expected output
    private static Stream<Arguments> provideSolarPanelAmountExamples() {
        return Stream.of(
                Arguments.of(14, 7, 0.0f),
                Arguments.of(14, 8, 0.25f),
                Arguments.of(14, 13, 2.75f),
                Arguments.of(24, 13, 4.714285714f),
                Arguments.of(33, 13, 6.482142857f),
                Arguments.of(99, 13, 19.44642857f)
        );
    }

    // Invalid hour throws an IllegalArgumentException
    @Test
    @DisplayName("Invalid Hour Test")
    public void InvalidHourTest() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);
        assertThrows(IllegalArgumentException.class, () -> house.getSolarPanelConsumptionAtHour(24));
    }

    // Adding a negative amount of solar panels throws an IllegalArgumentException
    @Test
    @DisplayName("Add Negative Solar Panel Amount Test")
    public void AddNegativeSolarPanelAmountTest() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);
        assertThrows(IllegalArgumentException.class, () -> house.addSolarPanel(-1));
    }

    // Removing a negative amount of solar panels throws an IllegalArgumentException
    @Test
    @DisplayName("Remove Negative Solar Panel Amount Test")
    public void RemoveNegativeSolarPanelAmountTest() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);
        assertThrows(IllegalArgumentException.class, () -> house.removeSolarPanel(-1));
    }

    // Removing more solar panels than there are throws an IllegalArgumentException
    @Test
    @DisplayName("Remove More Solar Panels Than There Are Test")
    public void RemoveMoreSolarPanelsThanThereAreTest() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);
        assertThrows(IllegalArgumentException.class, () -> house.removeSolarPanel(2));
    }

    // Removing 1 solar panel from a house with 1 solar panel should result in 0 solar panels
    @Test
    @DisplayName("Remove Right Amount Of Solar Panels Test")
    public void RemoveRightAmountOfSolarPanelsTest() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);
        house.removeSolarPanel(1);
        assertEquals(0, house.getTotalSolarPanels());
    }
}
