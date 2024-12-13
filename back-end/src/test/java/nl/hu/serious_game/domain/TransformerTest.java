package nl.hu.serious_game.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
public class TransformerTest {

    @Test
    @DisplayName("test for one house without batteries")
    public void simpleTest() {
        House mockHouse = Mockito.mock(House.class);
        Electricity expected1 = new Electricity(3F, Direction.DEMAND);
        Electricity expected2 = new Electricity(1F, Direction.PRODUCTION);
        when(mockHouse.current(1)).thenReturn(expected1);
        when(mockHouse.current(2)).thenReturn(expected2);

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);

        Transformer transformer = new Transformer(1, houses, 0);
        assertEquals(expected1, transformer.calculateLeftoverCurrent(1));
        assertEquals(expected2, transformer.calculateLeftoverCurrent(2));
    }

    @Test
    @DisplayName("test for one house and battery")
    public void batteryTest() {
        House mockHouse = Mockito.mock(House.class);
        when(mockHouse.current(1)).thenReturn(new Electricity(6F, Direction.PRODUCTION));
        when(mockHouse.current(2)).thenReturn(new Electricity(7F, Direction.DEMAND));

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);

        Electricity expected1 = new Electricity(1F, Direction.PRODUCTION);
        Electricity expected2 = new Electricity(2F, Direction.DEMAND);

        // Adding a second battery would break the test
        // because all 6 of the first getcurrent would go into the battery.
        Transformer transformer = new Transformer(1, houses, 1);

        assertEquals(expected1, transformer.calculateLeftoverCurrent(1));
        assertEquals(expected2, transformer.calculateLeftoverCurrent(2));
    }

    @Test
    @DisplayName("test with multiple houses")
    public void multipleHousesTest() {
        House mockHouse1 = Mockito.mock(House.class);
        when(mockHouse1.current(1)).thenReturn(new Electricity(6F, Direction.PRODUCTION));
        when(mockHouse1.current(2)).thenReturn(new Electricity(6F, Direction.DEMAND));
        when(mockHouse1.current(3)).thenReturn(new Electricity(7F, Direction.DEMAND));
        when(mockHouse1.current(4)).thenReturn(new Electricity(7F, Direction.PRODUCTION));

        House mockHouse2 = Mockito.mock(House.class);
        when(mockHouse2.current(1)).thenReturn(new Electricity(4F, Direction.DEMAND));
        when(mockHouse2.current(2)).thenReturn(new Electricity(4F, Direction.PRODUCTION));
        when(mockHouse2.current(3)).thenReturn(new Electricity(2F, Direction.DEMAND));
        when(mockHouse2.current(4)).thenReturn(new Electricity(2F, Direction.PRODUCTION));

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse1);
        houses.add(mockHouse2);

        Electricity expected1 = new Electricity(2F, Direction.PRODUCTION);
        Electricity expected2 = new Electricity(2F, Direction.DEMAND);
        Electricity expected3 = new Electricity(9F, Direction.DEMAND);
        Electricity expected4 = new Electricity(9F, Direction.PRODUCTION);

        Transformer transformer = new Transformer(1, houses, 0);

        // Checks if production and demand get compensated for eachother.
        assertEquals(expected1, transformer.calculateLeftoverCurrent(1));
        assertEquals(expected2, transformer.calculateLeftoverCurrent(2));
        // Checks if the values get added when the direction is the same.
        assertEquals(expected3, transformer.calculateLeftoverCurrent(3));
        assertEquals(expected4, transformer.calculateLeftoverCurrent(4));
    }

    @Test
    @DisplayName("test for multiple houses and a battery")
    public void multiHouseBatteryTest() {
        House mockHouse1 = Mockito.mock(House.class);
        when(mockHouse1.current(1)).thenReturn(new Electricity(3F, Direction.PRODUCTION));

        House mockHouse2 = Mockito.mock(House.class);
        when(mockHouse2.current(1)).thenReturn(new Electricity(3F, Direction.PRODUCTION));

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse1);
        houses.add(mockHouse2);

        // 5 goes into the battery and 1 is left over.
        Electricity expected = new Electricity(1F, Direction.PRODUCTION);

        Transformer transformer = new Transformer(1, houses, 1);

        assertEquals(expected, transformer.calculateLeftoverCurrent(1));
    }

    @Test
    @DisplayName("low maxCurrent")
    public void LowMaxCurrentTest() {
        House mockHouse = Mockito.mock(House.class);
        when(mockHouse.current(1)).thenReturn(new Electricity(2F, Direction.PRODUCTION));
        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);
        Transformer transformer = new Transformer(1, houses, 0, new Congestion(true, 1));
        Electricity electricity = transformer.calculateLeftoverCurrent(1);
        Electricity excess = transformer.getExcessCurrent();
        assertAll(
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(1f, electricity.amount()),
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(1f, excess.amount(), 0.001)
        );
    }

    @Test
    @DisplayName("high maxCurrent")
    public void HighMaxCurrentTest() {
        House mockHouse = Mockito.mock(House.class);
        when(mockHouse.current(1)).thenReturn(new Electricity(2F, Direction.PRODUCTION));
        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);
        Transformer transformer = new Transformer(1, houses, 0, new Congestion(true, 10));
        Electricity electricity = transformer.calculateLeftoverCurrent(1);
        Electricity excess = transformer.getExcessCurrent();
        assertAll(
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(2f, electricity.amount(), 0.001),
                () -> assertEquals(Direction.PRODUCTION, electricity.direction()),
                () -> assertEquals(0f, excess.amount())
        );
    }
}
