package nl.hu.serious_game.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
public class TransformerTest {
    @Test
    @DisplayName("test for one house without batteries")
    public void simpleTest() {
        House mockHouse = Mockito.mock(House.class);
        when(mockHouse.getCurrent()).thenReturn();
        List<House> houses = new ArrayList<>();
        houses.add(mockHouse);
        Transformer transformer = new Transformer(houses, 0, 0, false);
    }
}
