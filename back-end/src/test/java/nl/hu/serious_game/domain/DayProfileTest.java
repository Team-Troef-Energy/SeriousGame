package nl.hu.serious_game.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DayProfileTest {
    @Test
    @DisplayName("Template Test")
    public void TemplateTest() {
        assertTrue(true);
    }

    // Test for checking value of 08:00 for a single solar panel during summer
    // Base Solar panel value at 08:00 is documented to be: 0.017857143
    // Summer factor is: 1
    // Expected value is: 0.017857143
    @Test
    @DisplayName("Summer Test with one solar panel 08:00")
    public void SummerTestOneSolarPanelHour8() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        assertEquals(0.0179, dayProfile.getValue(8, "SolarPanelProduction"));
    }

    // Test for checking value of 07:00 for a single solar panel during summer
    // Base Solar panel value at 07:00 is documented to be: 0
    // Summer factor is: 1
    // Expected value is: 0
    @Test
    @DisplayName("Summer Test with one solar panel 07:00")
    public void SummerTestOneSolarPanelHour7() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        assertEquals(0, dayProfile.getValue(7, "SolarPanelProduction"));
    }

    // Test for checking value of 13:00 for a single solar panel during summer
    // Base Solar panel value at 13:00 is documented to be: 0.196428571
    // Summer factor is: 1
    // Expected value is: 0.196428571
    @Test
    @DisplayName("Summer Test with one solar panel 13:00")
    public void SummerTestOneSolarPanelHour13() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        assertEquals(0.1964, dayProfile.getValue(13, "SolarPanelProduction"));
    }

    // Test for checking value of 13:00 for a single solar panel during winter
    // Base Solar panel value at 13:00 is documented to be: 0.196428571
    // Winter factor is: 0.27
    // Expected value is: 0.05303571417
    @Test
    @DisplayName("Winter Test with one solar panel 13:00")
    public void WinterTestOneSolarPanelHour13() {
        DayProfile dayProfile = new DayProfile(Season.WINTER);
        assertEquals(0.0530, dayProfile.getValue(13, "SolarPanelProduction"));
    }

    // Test for checking value of 13:00 for a single solar panel during spring
    // Base Solar panel value at 13:00 is documented to be: 0.196428571
    // Spring factor is: 0.89
    // Expected value is: 0.17482142819
    @Test
    @DisplayName("Spring Test with one solar panel 13:00")
    public void SpringTestOneSolarPanelHour13() {
        DayProfile dayProfile = new DayProfile(Season.SPRING);
        assertEquals(0.1748, dayProfile.getValue(13, "SolarPanelProduction"));
    }

    // Test for checking value of 13:00 for a single solar panel during autumn
    // Base Solar panel value at 13:00 is documented to be: 0.196428571
    // Autumn factor is: 0.54
    // Expected value is: 0.10607142834
    @Test
    @DisplayName("Autumn Test with one solar panel 13:00")
    public void AutumnTestOneSolarPanelHour13() {
        DayProfile dayProfile = new DayProfile(Season.AUTUMN);
        assertEquals(0.1061, dayProfile.getValue(13, "SolarPanelProduction"));
    }

    // Invalid hour throws exception
    @Test
    @DisplayName("Invalid hour throws exception")
    public void InvalidHourThrowsException() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        assertThrows(IllegalArgumentException.class, () -> dayProfile.getValue(25, "SolarPanelProduction"));
    }

    // Invalid column throws exception
    @Test
    @DisplayName("Invalid column throws exception")
    public void InvalidColumnThrowsException() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        assertThrows(IllegalArgumentException.class, () -> dayProfile.getValue(13, "RandomColumnNameThatDoesntExist"));
    }
}
