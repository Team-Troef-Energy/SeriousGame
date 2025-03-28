package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Congestion;

import java.util.List;

public record GameTransformerDTO(
        int id,
        int templateId,
        CurrentDTO current,
        Congestion congestion,
        List<GameHouseDTO> houses,
        BatteryDTO batteries,
        int maxBatteryCount) {
}
