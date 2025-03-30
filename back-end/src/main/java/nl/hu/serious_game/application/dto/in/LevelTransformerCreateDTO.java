package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.domain.Congestion;

import java.util.List;

public record LevelTransformerCreateDTO(
    Congestion congestion,
    List<LevelHouseCreateDTO> houses,
    int maxBatteryCount
) {}
