package nl.hu.serious_game.domain.HouseTest;

import static org.junit.jupiter.api.Assertions.*;

import nl.hu.serious_game.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameHouseCurrentTest {
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
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 14);

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
        GameHouse house = new GameHouse(new LevelHouse(dayProfile, new HouseOptions()), 1);

        assertAll(
            () -> assertEquals(Direction.DEMAND, house.getCurrentAtHour(12).getDirection()),
            () -> assertEquals(0.193571429f, house.getCurrentAtHour(12).getAmount(), 0.0000001)
        );
    }
}
