package nl.hu.serious_game.domain.HouseTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nl.hu.serious_game.domain.Congestion;
import nl.hu.serious_game.domain.DayProfile;
import nl.hu.serious_game.domain.Direction;
import nl.hu.serious_game.domain.Electricity;
import nl.hu.serious_game.domain.House;
import nl.hu.serious_game.domain.HouseOptions;
import nl.hu.serious_game.domain.Season;

public class HouseCongestionTest {
    @Test
    @DisplayName("low maxCurrent")
    public void LowMaxCurrentTest() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        House house = new House(1, 14, dayProfile, new HouseOptions(false, false, new Congestion(true, 1)));
        Electricity electricity = house.getElectricityAtHour(12);
        Electricity excess = house.getExcessCurrent();
        assertAll(
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(1f, electricity.amount()),
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(1.36f, excess.amount(), 0.001)
        );
    }

    @Test
    @DisplayName("high maxCurrent")
    public void HighMaxCurrentTest() {
        DayProfile dayProfile = new DayProfile(Season.SUMMER);
        House house = new House(1, 14, dayProfile, new HouseOptions(false, false, new Congestion(true, 10)));
        Electricity electricity = house.getElectricityAtHour(12);
        Electricity excess = house.getExcessCurrent();
        assertAll(
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(2.36f, electricity.amount(), 0.001),
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(0f, excess.amount())
        );
    }
}
