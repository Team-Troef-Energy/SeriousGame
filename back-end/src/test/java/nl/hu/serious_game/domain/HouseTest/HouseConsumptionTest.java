package nl.hu.serious_game.domain.HouseTest;

import nl.hu.serious_game.domain.DayProfile;
import nl.hu.serious_game.domain.House;
import nl.hu.serious_game.domain.Season;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HouseConsumptionTest {
    @Test
    @DisplayName("Template Test")
    public void TemplateTest() {
        assertTrue(true);
    }

    // Test for checking what the house consumption is at 12:00 during summer
    // Base house consumption at 12:00 is documented to be: 0.39
    // Expected value is: 0.39
    @Test
    @DisplayName("House Consumption Test 12:00")
    public void HouseConsumptionTestHour12() {
        House house = new House(1, 1, new DayProfile(Season.SUMMER));
        assertEquals(0.39f, house.getConsumption(12));
    }

    // Parameterized test for checking the house consumption for different seasons and hours
    @ParameterizedTest
    @DisplayName("Season Test house consumption")
    @MethodSource("provideHouseConsumptionSeasonAndTimeExamples")
    public void SeasonTestHouseConsumption(Season season, int hour, float expectedOutput) {
        House house = new House(1, 1, new DayProfile(season));
        assertEquals(expectedOutput, house.getConsumption(hour));
    }

    // Source of arguments for the parameterized test, containing: Season, Hour, Expected output
    private static Stream<Arguments> provideHouseConsumptionSeasonAndTimeExamples() {
        return Stream.of(
                Arguments.of(Season.SUMMER, 12, 0.39f),
                Arguments.of(Season.WINTER, 12, 0.585f),
                Arguments.of(Season.SPRING, 12, 0.48749998f),
                Arguments.of(Season.AUTUMN, 12, 0.48749998f),
                Arguments.of(Season.SUMMER, 0, 0.277f),
                Arguments.of(Season.WINTER, 0, 0.41550002f),
                Arguments.of(Season.SPRING, 0, 0.34625f),
                Arguments.of(Season.AUTUMN, 0, 0.34625f)
        );
    }
}
