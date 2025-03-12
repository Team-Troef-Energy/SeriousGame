package nl.hu.serious_game.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BatteryTest {
    @Test
    @DisplayName("test for when all current production can be handled")
    public void chargeAllTest() {
        Battery battery = new Battery(1);
        Current input = new Current(3f, Direction.PRODUCTION);
        Current expected = new Current(0f, Direction.PRODUCTION);
        assertEquals(battery.chargeOrDischarge(input), expected);
        assertEquals(battery.getCurrentCharge(), 3f);
    }

    @Test
    @DisplayName("test for when some production should be left over")
    public void chargeMoreThanChargeSpeedTest() {
        Battery battery = new Battery(1);
        Current input = new Current(5.5f, Direction.PRODUCTION);
        Current expected = new Current(0.5f, Direction.PRODUCTION);
        assertEquals(battery.chargeOrDischarge(input), expected);
        assertEquals(battery.getCurrentCharge(), 5f);
    }

    @Test
    @DisplayName("attempt to charge beyond capacity")
    public void chargeMoreThanCapacityTest() {
        Battery battery = new Battery(1);
        Current current = new Current(5f, Direction.PRODUCTION);
        battery.chargeOrDischarge(current);
        battery.chargeOrDischarge(current);
        battery.chargeOrDischarge(current);
        assertEquals(battery.chargeOrDischarge(current), current);
        assertEquals(battery.getCurrentCharge(), 13.5f);
    }

    @Test
    @DisplayName("test for when there is more demand than currentCharge")
    public void fullDischargeTest() {
        Battery battery = new Battery(1);
        battery.chargeOrDischarge(new Current(5f, Direction.PRODUCTION));
        Current input = new Current(5.5f, Direction.DEMAND);
        Current expected = new Current(0.5f, Direction.DEMAND);
        assertEquals(battery.chargeOrDischarge(input), expected);
        assertEquals(battery.getCurrentCharge(), 0f);
    }

    @Test
    @DisplayName("test for when there is less demand than currentCharge")
    public void someDischargeTest() {
        Battery battery = new Battery(1);
        battery.chargeOrDischarge(new Current(5f, Direction.PRODUCTION));
        Current input = new Current(4.5f, Direction.DEMAND);
        Current expected = new Current(0f, Direction.DEMAND);
        assertEquals(battery.chargeOrDischarge(input), expected);
        assertEquals(battery.getCurrentCharge(), 0.5f);
    }

    @Test
    @DisplayName("attempt to pull more than dischargeSpeed")
    public void dischargeSpeedTest() {
        Battery battery = new Battery(1);
        battery.chargeOrDischarge(new Current(5f, Direction.PRODUCTION));
        battery.chargeOrDischarge(new Current(5f, Direction.PRODUCTION));
        battery.chargeOrDischarge(new Current(5f, Direction.PRODUCTION));
        Current input = new Current(12f, Direction.DEMAND);
        Current expected = new Current(0.5f, Direction.DEMAND);
        assertEquals(battery.chargeOrDischarge(input), expected);
        assertEquals(battery.getCurrentCharge(), 2f);
    }

    @Test
    @DisplayName("Check that the constructor hasn't been messed up")
    public void constructorTest() {
        Battery battery = new Battery(1);
        Battery battery2 = new Battery(2);
        assertEquals(battery.getMaxCharge() * 2, battery2.getMaxCharge());
        assertEquals(battery.getChargeSpeed() * 2, battery2.getChargeSpeed());
        assertEquals(battery.getDischargeSpeed() * 2, battery2.getDischargeSpeed());
    }
}
