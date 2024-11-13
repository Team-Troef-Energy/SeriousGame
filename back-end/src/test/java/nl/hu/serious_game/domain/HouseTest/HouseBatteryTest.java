package nl.hu.serious_game.domain.HouseTest;

import nl.hu.serious_game.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HouseBatteryTest {

    private House house;

    @BeforeEach
    public void setUp() {
        this.house = new House(1, 14, new DayProfile(Season.SUMMER));
    }

    // One battery can be added to a house
    @Test
    @DisplayName("Add one battery to house")
    public void addOneBattery() {
        this.house.setBattery(1);
        assertAll(
                () -> assertNotNull(this.house.getBattery()),
                () -> assertDoesNotThrow(() -> this.house.setBattery(1))
        );
    }

    // Two batteries doubles the amount of stored power that can be stored
    // Capacity of 1 battery is 13.5 kW
    // Capacity of 2 batteries is 27 kW
    @Test
    @DisplayName("Add two batteries to house")
    public void addTwoBatteries() {
        this.house.setBattery(2);
        assertAll(
                () -> assertNotNull(this.house.getBattery()),
                () -> assertDoesNotThrow(() -> this.house.setBattery(2)),
                () -> assertEquals(27f, this.house.getBattery().getMaxCharge())
        );
    }

    // Adding a negative amount of batteries to a house should throw an IllegalArgumentException
    @Test
    @DisplayName("Add negative amount of batteries to house")
    public void addNegativeBatteries() {
        assertThrows(IllegalArgumentException.class, () -> this.house.setBattery(-1));
    }

    // A house with 14 solar panels should produce 2.75 kW in 1 hour at 12:00 during summer
    // A house should consume 0.39 kW in 1 hour at 12:00 during summer
    // 1 battery should store up to 5 kW in 1 hour
    // The house should produce 2.75 kW with its solar panels, consume 0.39 kW of that and
    // then store 2.36 kW in the battery. House has 0 kW of current left as everything can be stored.
    @Test
    @DisplayName("Battery can store everything and leaves house with 0 kW of current")
    public void batteryCanStoreEverythingTest() {
        this.house.setBattery(1);
        assertAll(
                () -> assertEquals(0f, house.getCurrent(12).amount()),
                () -> assertEquals(2.36f, house.getBattery().getCurrentCharge(), 0.000001f)
        );
    }

    // A house with 28 solar panels should produce 5.5 kW in 1 hour at 12:00 during summer
    // A house should consume 0.39 kW in 1 hour at 12:00 during summer
    // 1 battery should store up to 5 kW in 1 hour
    // The house should produce 5.5 kW with its solar panels, consume 0.39 kW of that and
    // then store 5.11 kW in the battery. House has 0.11 kW of current left with PRODUCTION direction.
    @Test
    @DisplayName("Battery can store everything and leaves house with 0.11 kW of current")
    public void batteryStoreMoreThanChargeSpeedTest() {
        this.house.setBattery(1);
        this.house.addSolarPanel(14);
        Electricity electricity = house.getCurrent(12);
        assertAll(
                () -> assertEquals(0.11f, electricity.amount(), 0.000001f),
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(5f, house.getBattery().getCurrentCharge())
        );
    }

    // A house with 14 solar panels should produce 0 kW in 1 hour at 19:00 during summer
    // A house should consume 0.39 kW in 1 hour at 19:00 during summer
    // 1 battery can store up to 5 kW
    // If the battery has 5 kW stored, the house should consume 0.39 kW from the battery,
    // leaving 4.61 kW in the battery. House has 0 kW of current left as everything can be consumed
    // from the battery.
    @Test
    @DisplayName("Battery can consume everything from battery and leaves house with 0 kW of current")
    public void houseCanConsumeEverythingFromBatteryTest() {
        this.house.setBattery(1);
        // "Mock" the battery to have 5 kW stored already
        this.house.getBattery().use(new Electricity(5f, Direction.PRODUCTION));
        Electricity electricity = house.getCurrent(19);
        assertAll(
                () -> assertEquals(0f, electricity.amount()),
                () -> assertEquals(4.61f, house.getBattery().getCurrentCharge(), 0.000001f)
        );
    }
}