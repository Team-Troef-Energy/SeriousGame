package nl.hu.serious_game.application.dto.out;

import java.util.List;

public record TransformerDTO(
        int id,
        CurrentDTO current,
        List<HouseDTO> houses,
        BatteryDTO batteries
) {
}
