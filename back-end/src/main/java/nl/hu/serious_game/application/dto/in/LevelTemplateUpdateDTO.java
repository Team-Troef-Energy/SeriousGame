package nl.hu.serious_game.application.dto.in;

import java.util.List;

import nl.hu.serious_game.application.dto.out.ObjectiveDTO;
import nl.hu.serious_game.domain.Cost;
import nl.hu.serious_game.domain.Season;

public record LevelTemplateUpdateDTO(
        Season season,
        int startTime,
        int endTime,
        ObjectiveDTO objective,
        List<LevelTransformerUpdateDTO> transformers,
        Cost cost) {
}
