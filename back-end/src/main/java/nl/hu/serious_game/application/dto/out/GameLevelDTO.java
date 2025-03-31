package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Cost;
import nl.hu.serious_game.domain.Season;

import java.util.List;

public record GameLevelDTO(
        long id,
        long templateId,
        List<HourDTO> hours,
        Season season,
        int startTime,
        int endTime,
        ObjectiveDTO objective,
        Cost cost,
        boolean isCompleted,
        int totalCosts,
        float totalCO2
) {}
