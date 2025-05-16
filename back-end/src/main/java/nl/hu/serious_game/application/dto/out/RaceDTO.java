package nl.hu.serious_game.application.dto.out;

import java.util.List;

public record RaceDTO(
        String name,
        String userEmail,
        List<LevelTemplateDTO> levels
) {}
