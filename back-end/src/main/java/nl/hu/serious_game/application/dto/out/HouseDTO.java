package nl.hu.serious_game.application.dto.out;

public record HouseDTO(
        int id,
        CurrentDTO current,
        BatteryDTO batteries,
        int solarpanels
) {
}
