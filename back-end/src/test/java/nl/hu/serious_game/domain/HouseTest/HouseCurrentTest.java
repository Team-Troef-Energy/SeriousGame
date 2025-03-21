package nl.hu.serious_game.domain.HouseTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.serious_game.domain.DayProfile;
import nl.hu.serious_game.domain.Direction;
import nl.hu.serious_game.domain.House;
import nl.hu.serious_game.domain.HouseOptions;
import nl.hu.serious_game.domain.Season;

public class HouseCurrentTest {
    @Test
    @DisplayName("Template Test")
    public void TemplateTest() {
        assertTrue(true);
    }

    // Test for checking what the house consumption is at 12:00 during summer with 14 solar panels
    // Base house consumption at 12:00 is documented to be: 0.39
    // Solar panel output at 12:00 during summer is documented to be: 2.75
    // Expected value is: 2.75 - 0.39 = 2.36 (PRODUCTION)
    @Test
    @DisplayName("Summer Test with 14 solar panels, house producing more than consuming")
    public void SummerTest14SolarPanels() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        House house = new House(14, dayProfile, new HouseOptions());

        assertAll(
            () -> assertEquals(Direction.PRODUCTION, house.getCurrentAtHour(12).getDirection()),
            () -> assertEquals(2.36f, house.getCurrentAtHour(12).getAmount(), 0.001)
        );
    }

    // Test for checking what the house consumption is at 12:00 during summer with 1 solar panel
    // Base house consumption at 12:00 is documented to be: 0.39
    // Solar panel output at 12:00 during summer is documented to be: 0.196428571
    // Expected value is: 0.196428571 - 0.39 = 0.193571429 (DEMAND)
    @Test
    @DisplayName("Summer Test with 1 solar panel, house consuming more than producing")
    public void SummerTest1SolarPanel() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        House house = new House(1, dayProfile, new HouseOptions());

        assertAll(
            () -> assertEquals(Direction.DEMAND, house.getCurrentAtHour(12).getDirection()),
            () -> assertEquals(0.193571429f, house.getCurrentAtHour(12).getAmount(), 0.0000001)
        );
    }
}
