package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Congestion;

import java.util.List;

public record TransformerDTO(
        int id,
        CurrentDTO current,
        Congestion congestion,
        List<HouseDTO> houses,
        BatteryDTO batteries
) {
}
