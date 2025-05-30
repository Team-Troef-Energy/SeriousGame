package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.application.dto.out.ObjectiveDTO;
import nl.hu.serious_game.domain.Cost;
import nl.hu.serious_game.domain.LevelType;
import nl.hu.serious_game.domain.Season;

import java.util.List;

public record LevelTemplateCreateDTO(
        int levelNumber,
        Season season,
        int startTime,
        int endTime,
        ObjectiveDTO objective,
        List<LevelTransformerCreateDTO> transformers,
        Cost cost,
        LevelType levelType,
        Long raceId
) {}
