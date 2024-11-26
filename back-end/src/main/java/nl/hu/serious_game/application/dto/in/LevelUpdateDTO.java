package nl.hu.serious_game.application.dto.in;

import java.util.List;

public record LevelUpdateDTO(
        List<TransformerUpdateDTO> transformers
) {}
