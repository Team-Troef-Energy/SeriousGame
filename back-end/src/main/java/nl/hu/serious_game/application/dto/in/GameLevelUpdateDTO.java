package nl.hu.serious_game.application.dto.in;

import java.util.List;

public record GameLevelUpdateDTO(
        List<GameTransformerUpdateDTO> transformers
) {}
