package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Season;

import java.util.List;

public record LevelTemplateDTO(
        long id,
        Season season,
        int startTime,
        int endTime,
        ObjectiveDTO objective,
        List<LevelTransformerDTO> transformers
) {}
