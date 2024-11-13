package nl.hu.serious_game.application.dto.out;

import java.util.List;

public record TransformerDTO(
        String id,
        CurrentDTO current,
        List<HouseDTO> houses,
        BatteryDTO batteries
) {
}
