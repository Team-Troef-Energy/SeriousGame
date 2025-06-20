package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Race;

import java.util.List;

public record RaceDTO(
        Long id,
        String name,
        String userEmail,
        List<LevelTemplateDTO> levels
) {
    public static RaceDTO fromEntity(Race race) {
        return new RaceDTO(
                race.getId(),
                race.getName(),
                race.getUserEmail(),
                race.getLevels().stream().map(LevelTemplateDTO::fromEntity).toList()
        );
    }
}
