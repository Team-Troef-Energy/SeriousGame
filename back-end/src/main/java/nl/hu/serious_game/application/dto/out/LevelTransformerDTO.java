package nl.hu.serious_game.application.dto.out;

import java.util.List;

public record LevelTransformerDTO(
        int id,
        boolean hasCongestion,
        float maxCurrent,
        int maxBatteryCount,
        List<LevelHouseDTO> houses
) {
}
