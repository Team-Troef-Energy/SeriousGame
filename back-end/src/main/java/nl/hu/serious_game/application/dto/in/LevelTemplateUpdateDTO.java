package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.domain.Congestion;
import nl.hu.serious_game.domain.LevelTransformer;
import nl.hu.serious_game.domain.Objective;
import nl.hu.serious_game.domain.Season;

import java.util.List;

public record LevelTemplateUpdateDTO(
        Season season,
        int startTime,
        int endTime,
        Objective objective,
        List<LevelTransformerUpdateDTO> transformers
) {}
