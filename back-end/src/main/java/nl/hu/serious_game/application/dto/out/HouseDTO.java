package nl.hu.serious_game.application.dto.out;

public record HouseDTO(
        String id,
        CurrentDTO current,
        BatteryDTO batteries,
        int solarpanels
) {
}
