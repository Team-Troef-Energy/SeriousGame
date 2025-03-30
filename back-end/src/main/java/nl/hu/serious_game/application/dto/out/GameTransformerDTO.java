package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Congestion;

import java.util.List;

public record GameTransformerDTO(
        long id,
        long templateId,
        CurrentDTO current,
        Congestion congestion,
        List<GameHouseDTO> houses,
        BatteryDTO batteries,
        int maxBatteryCount) {
}
