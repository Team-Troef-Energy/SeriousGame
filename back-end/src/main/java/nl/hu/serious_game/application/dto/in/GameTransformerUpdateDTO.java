package nl.hu.serious_game.application.dto.in;

import java.util.List;

public record GameTransformerUpdateDTO(
        long id,
        List<GameHouseUpdateDTO> houses,
        int batteries
) {}

