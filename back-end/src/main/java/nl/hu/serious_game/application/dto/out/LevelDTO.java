package nl.hu.serious_game.application.dto.out;

import java.util.List;

public record LevelDTO(
        List<HourDTO> hours,
        String season,
        int startTime,
        int endTime,
        ObjectiveDTO objective
) {}

