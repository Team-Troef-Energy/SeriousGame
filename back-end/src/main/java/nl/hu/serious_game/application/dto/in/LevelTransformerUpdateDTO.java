package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.domain.Congestion;
import nl.hu.serious_game.domain.LevelHouse;

import java.util.List;

public record LevelTransformerUpdateDTO(
        long id,
        Congestion congestion,
        List<LevelHouseUpdateDTO> houses,
        int maxBatteryCount
) {}
