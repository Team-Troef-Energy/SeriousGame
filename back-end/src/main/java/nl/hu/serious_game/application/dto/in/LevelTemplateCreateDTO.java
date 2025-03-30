package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.application.dto.out.ObjectiveDTO;
import nl.hu.serious_game.domain.Season;

import java.util.List;

public record LevelTemplateCreateDTO(
        Season season,
        int startTime,
        int endTime,
        ObjectiveDTO objective,
        List<LevelTransformerCreateDTO> transformers
) {}
