package nl.hu.serious_game.domain.HouseTest;

import nl.hu.serious_game.domain.DayProfile;
import nl.hu.serious_game.domain.House;
import nl.hu.serious_game.domain.HouseOptions;
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
        House house = new House(1, 1, new DayProfile(Season.SUMMER), new HouseOptions());
        assertEquals(0.39f, house.getBaseConsumption(12));
    }

    // Parameterized test for checking the house consumption for different seasons and hours
    @ParameterizedTest
    @DisplayName("Season Test house consumption")
    @MethodSource("provideHouseConsumptionSeasonAndTimeExamples")
    public void SeasonTestHouseConsumption(Season season, int hour, float expectedOutput) {
        House house = new House(1, 1, new DayProfile(season), new HouseOptions());
        assertEquals(expectedOutput, house.getBaseConsumption(hour));
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

    // Parameterized test for checking the house consumption for different options
    // (electric vehicle and heatpump) and hours
    // Heatpump has a consumption of 0.1825 throughout the day
    // Electric vehicle has a consumption of 0.36 between 19:00 and 07:00 the next day
    @ParameterizedTest
    @DisplayName("House Consumption Test with options")
    @MethodSource("provideHouseConsumptionOptionsAndTimeExamples")
    public void HouseConsumptionTestOptions(Season season, boolean hasElectricVehicle, boolean hasHeatPump, int hour, float expectedOutput) {
        House house = new House(1, 1, new DayProfile(season), new HouseOptions(hasHeatPump, hasElectricVehicle));
        assertEquals(expectedOutput, house.getTotalConsumptionOfHour(hour));
    }

    // Source of arguments for the parameterized test, containing: Season, Electric vehicle, Heat pump, Hour, Expected output
    private static Stream<Arguments> provideHouseConsumptionOptionsAndTimeExamples() {
        return Stream.of(
                // Tests for when electric vehicle will not consume due to the hour
                Arguments.of(Season.SUMMER, false, false, 12, 0.39f),
                Arguments.of(Season.SUMMER, true, false, 12, 0.39f),
                Arguments.of(Season.SUMMER, false, true, 12, 0.5725f),
                Arguments.of(Season.SUMMER, true, true, 12, 0.5725f),
                Arguments.of(Season.WINTER, false, false, 12, 0.585f),
                Arguments.of(Season.WINTER, true, false, 12, 0.585f),
                Arguments.of(Season.WINTER, false, true, 12, 0.95f),
                Arguments.of(Season.WINTER, true, true, 12, 0.95f),
                Arguments.of(Season.SPRING, false, false, 12, 0.48749998f),
                Arguments.of(Season.SPRING, true, false, 12, 0.48749998f),
                Arguments.of(Season.SPRING, false, true, 12, 0.704675f),
                Arguments.of(Season.SPRING, true, true, 12, 0.704675f),
                Arguments.of(Season.AUTUMN, false, false, 12, 0.48749998f),
                Arguments.of(Season.AUTUMN, true, false, 12, 0.48749998f),
                Arguments.of(Season.AUTUMN, false, true, 12, 0.784975f),
                Arguments.of(Season.AUTUMN, true, true, 12, 0.784975f),

                // Same tests, but for when electric vehicle will also consume
                Arguments.of(Season.SUMMER, false, false, 0, 0.277f),
                Arguments.of(Season.SUMMER, true, false, 0, 0.637f),
                Arguments.of(Season.SUMMER, false, true, 0, 0.4595f),
                Arguments.of(Season.SUMMER, true, true, 0, 0.8195f),
                Arguments.of(Season.WINTER, false, false, 0, 0.41550002f),
                Arguments.of(Season.WINTER, true, false, 0, 0.77550006f),
                Arguments.of(Season.WINTER, false, true, 0, 0.78050005f),
                Arguments.of(Season.WINTER, true, true, 0, 1.1405001f),
                Arguments.of(Season.SPRING, false, false, 0, 0.34625f),
                Arguments.of(Season.SPRING, true, false, 0, 0.70625f),
                Arguments.of(Season.SPRING, false, true, 0, 0.563425f),
                Arguments.of(Season.SPRING, true, true, 0, 0.923425f),
                Arguments.of(Season.AUTUMN, false, false, 0, 0.34625f),
                Arguments.of(Season.AUTUMN, true, false, 0, 0.70625f),
                Arguments.of(Season.AUTUMN, false, true, 0, 0.64372504f),
                Arguments.of(Season.AUTUMN, true, true, 0, 1.003725f)
        );
    }
}
