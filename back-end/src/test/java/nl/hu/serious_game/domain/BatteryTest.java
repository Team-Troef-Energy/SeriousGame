package nl.hu.serious_game.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BatteryTest {
    @Test
    @DisplayName("test for when all electricity production can be handled")
    public void chargeAllTest() {
        Battery battery = new Battery(1);
        Electricity input = new Electricity(3f, Direction.PRODUCTION);
        Electricity expected = new Electricity(0f, Direction.PRODUCTION);
        assertEquals(battery.use(input), expected);
        assertEquals(battery.getCurrentCharge(), 3f);
    }

    @Test
    @DisplayName("test for when some production should be left over")
    public void chargeMoreThanChargeSpeedTest() {
        Battery battery = new Battery(1);
        Electricity input = new Electricity(5.5f, Direction.PRODUCTION);
        Electricity expected = new Electricity(0.5f, Direction.PRODUCTION);
        assertEquals(battery.use(input), expected);
        assertEquals(battery.getCurrentCharge(), 5f);
    }

    @Test
    @DisplayName("attempt to charge beyond capacity")
    public void chargeMoreThanCapacityTest() {
        Battery battery = new Battery(1);
        Electricity electricity = new Electricity(5f, Direction.PRODUCTION);
        battery.use(electricity);
        battery.use(electricity);
        battery.use(electricity);
        assertEquals(battery.use(electricity), electricity);
        assertEquals(battery.getCurrentCharge(), 13.5f);
    }

    @Test
    @DisplayName("test for when there is more demand than currentCharge")
    public void fullDischargeTest() {
        Battery battery = new Battery(1);
        battery.use(new Electricity(5f, Direction.PRODUCTION));
        Electricity input = new Electricity(5.5f, Direction.DEMAND);
        Electricity expected = new Electricity(0.5f, Direction.DEMAND);
        assertEquals(battery.use(input), expected);
        assertEquals(battery.getCurrentCharge(), 0f);
    }

    @Test
    @DisplayName("test for when there is less demand than currentCharge")
    public void someDischargeTest() {
        Battery battery = new Battery(1);
        battery.use(new Electricity(5f, Direction.PRODUCTION));
        Electricity input = new Electricity(4.5f, Direction.DEMAND);
        Electricity expected = new Electricity(0f, Direction.DEMAND);
        assertEquals(battery.use(input), expected);
        assertEquals(battery.getCurrentCharge(), 0.5f);
    }

    @Test
    @DisplayName("attempt to pull more than dischargeSpeed")
    public void dischargeSpeedTest() {
        Battery battery = new Battery(1);
        battery.use(new Electricity(5f, Direction.PRODUCTION));
        battery.use(new Electricity(5f, Direction.PRODUCTION));
        battery.use(new Electricity(5f, Direction.PRODUCTION));
        Electricity input = new Electricity(12f, Direction.DEMAND);
        Electricity expected = new Electricity(0.5f, Direction.DEMAND);
        assertEquals(battery.use(input), expected);
        assertEquals(battery.getCurrentCharge(), 2f);
    }

    @Test
    @DisplayName("Check that the constructor hasn't been messed up")
    public void constructorTest() {
        Battery battery = new Battery(1);
        Battery battery2 = new Battery(2);
        assertEquals(battery.getCapacity() * 2, battery2.getCapacity());
        assertEquals(battery.getChargeSpeed() * 2, battery2.getChargeSpeed());
        assertEquals(battery.getDischargeSpeed() * 2, battery2.getDischargeSpeed());
    }
}
