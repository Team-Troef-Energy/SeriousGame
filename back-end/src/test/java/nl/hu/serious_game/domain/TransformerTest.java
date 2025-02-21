package nl.hu.serious_game.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
public class TransformerTest {

    @Test
    @DisplayName("test for one house without batteries")
    public void simpleTest() {
        House mockHouse = Mockito.mock(House.class);
        Current expected1 = new Current(3F, Direction.DEMAND);
        Current expected2 = new Current(1F, Direction.PRODUCTION);
        when(mockHouse.getCurrentAtHour(1)).thenReturn(expected1);
        when(mockHouse.getCurrentAtHour(2)).thenReturn(expected2);

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);

        Transformer transformer = new Transformer(1, houses, 0);
        assertEquals(expected1, transformer.getCalculatedLeftoverCurrentAtHour(1));
        assertEquals(expected2, transformer.getCalculatedLeftoverCurrentAtHour(2));
    }

    @Test
    @DisplayName("test for one house and battery")
    public void batteryTest() {
        House mockHouse = Mockito.mock(House.class);
        when(mockHouse.getCurrentAtHour(1)).thenReturn(new Current(6F, Direction.PRODUCTION));
        when(mockHouse.getCurrentAtHour(2)).thenReturn(new Current(7F, Direction.DEMAND));

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);

        Current expected1 = new Current(1F, Direction.PRODUCTION);
        Current expected2 = new Current(2F, Direction.DEMAND);

        // Adding a second battery would break the test
        // because all 6 of the first getcurrent would go into the battery.
        Transformer transformer = new Transformer(1, houses, 1);

        assertEquals(expected1, transformer.getCalculatedLeftoverCurrentAtHour(1));
        assertEquals(expected2, transformer.getCalculatedLeftoverCurrentAtHour(2));
    }

    @Test
    @DisplayName("test with multiple houses")
    public void multipleHousesTest() {
        House mockHouse1 = Mockito.mock(House.class);
        when(mockHouse1.getCurrentAtHour(1)).thenReturn(new Current(6F, Direction.PRODUCTION));
        when(mockHouse1.getCurrentAtHour(2)).thenReturn(new Current(6F, Direction.DEMAND));
        when(mockHouse1.getCurrentAtHour(3)).thenReturn(new Current(7F, Direction.DEMAND));
        when(mockHouse1.getCurrentAtHour(4)).thenReturn(new Current(7F, Direction.PRODUCTION));

        House mockHouse2 = Mockito.mock(House.class);
        when(mockHouse2.getCurrentAtHour(1)).thenReturn(new Current(4F, Direction.DEMAND));
        when(mockHouse2.getCurrentAtHour(2)).thenReturn(new Current(4F, Direction.PRODUCTION));
        when(mockHouse2.getCurrentAtHour(3)).thenReturn(new Current(2F, Direction.DEMAND));
        when(mockHouse2.getCurrentAtHour(4)).thenReturn(new Current(2F, Direction.PRODUCTION));

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse1);
        houses.add(mockHouse2);

        Current expected1 = new Current(2F, Direction.PRODUCTION);
        Current expected2 = new Current(2F, Direction.DEMAND);
        Current expected3 = new Current(9F, Direction.DEMAND);
        Current expected4 = new Current(9F, Direction.PRODUCTION);

        Transformer transformer = new Transformer(1, houses, 0);

        // Checks if production and demand get compensated for eachother.
        assertEquals(expected1, transformer.getCalculatedLeftoverCurrentAtHour(1));
        assertEquals(expected2, transformer.getCalculatedLeftoverCurrentAtHour(2));
        // Checks if the values get added when the direction is the same.
        assertEquals(expected3, transformer.getCalculatedLeftoverCurrentAtHour(3));
        assertEquals(expected4, transformer.getCalculatedLeftoverCurrentAtHour(4));
    }

    @Test
    @DisplayName("test for multiple houses and a battery")
    public void multiHouseBatteryTest() {
        House mockHouse1 = Mockito.mock(House.class);
        when(mockHouse1.getCurrentAtHour(1)).thenReturn(new Current(3F, Direction.PRODUCTION));

        House mockHouse2 = Mockito.mock(House.class);
        when(mockHouse2.getCurrentAtHour(1)).thenReturn(new Current(3F, Direction.PRODUCTION));

        List<House> houses = new ArrayList<>();
        houses.add(mockHouse1);
        houses.add(mockHouse2);

        // 5 goes into the battery and 1 is left over.
        Current expected = new Current(1F, Direction.PRODUCTION);

        Transformer transformer = new Transformer(1, houses, 1);

        assertEquals(expected, transformer.getCalculatedLeftoverCurrentAtHour(1));
    }

    @Test
    @DisplayName("low maxCurrent")
    public void LowMaxCurrentTest() {
        House mockHouse = Mockito.mock(House.class);
        when(mockHouse.getCurrentAtHour(1)).thenReturn(new Current(2F, Direction.PRODUCTION));
        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);
        Transformer transformer = new Transformer(1, houses, 0, new Congestion(true, 1));
        Current current = transformer.getCalculatedLeftoverCurrentAtHour(1);
        Current excess = transformer.getExcessCurrent();
        assertAll(
                () -> assertEquals(Direction.PRODUCTION, current.direction()),
                () -> assertEquals(1f, current.amount()),
                () -> assertEquals(Direction.PRODUCTION, current.direction()),
                () -> assertEquals(1f, excess.amount(), 0.001)
        );
    }

    @Test
    @DisplayName("high maxCurrent")
    public void HighMaxCurrentTest() {
        House mockHouse = Mockito.mock(House.class);
        when(mockHouse.getCurrentAtHour(1)).thenReturn(new Current(2F, Direction.PRODUCTION));
        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);
        Transformer transformer = new Transformer(1, houses, 0, new Congestion(true, 10));
        Current current = transformer.getCalculatedLeftoverCurrentAtHour(1);
        Current excess = transformer.getExcessCurrent();
        assertAll(
                () -> assertEquals(Direction.PRODUCTION, current.direction()),
                () -> assertEquals(2f, current.amount(), 0.001),
                () -> assertEquals(Direction.PRODUCTION, current.direction()),
                () -> assertEquals(0f, excess.amount())
        );
    }
}
