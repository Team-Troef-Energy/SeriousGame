package nl.hu.serious_game.application.dto.in;

import java.util.List;

import nl.hu.serious_game.domain.Cost;
import nl.hu.serious_game.domain.Objective;
import nl.hu.serious_game.domain.Season;

public record LevelTemplateUpdateDTO(
                Season season,
                int startTime,
                int endTime,
                Objective objective,
                List<LevelTransformerUpdateDTO> transformers,
                Cost cost) {
}
