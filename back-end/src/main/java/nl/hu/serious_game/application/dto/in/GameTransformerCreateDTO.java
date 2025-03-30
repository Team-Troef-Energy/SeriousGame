package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.domain.Congestion;

import java.util.List;

public record GameTransformerCreateDTO(
        List<GameHouseCreateDTO> houses,
        int batteries,
        Congestion congestion
) {}
