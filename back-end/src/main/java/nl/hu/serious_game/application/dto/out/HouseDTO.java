package nl.hu.serious_game.application.dto.out;

import java.util.ArrayList;

public record HouseDTO(
        int id,
        CurrentDTO current,
        ArrayList<BatteryDTO> batteries,
        int solarpanels
) {
}
