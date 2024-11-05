package nl.hu.serious_game.application;

import jakarta.transaction.Transactional;
import nl.hu.serious_game.domain.House;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

    public HouseService() {
    }

    public void addSolarPanel(House house, int amount) {
        house.addSolarPanel(amount);
    }

    public void removeSolarPanel(House house, int amount) {
        house.removeSolarPanel(amount);
    }
}
