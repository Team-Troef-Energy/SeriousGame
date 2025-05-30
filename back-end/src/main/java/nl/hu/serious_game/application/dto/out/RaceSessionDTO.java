package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Race;
import nl.hu.serious_game.domain.RaceSession;

public record RaceSessionDTO(
        Long id,
        Race race,
        String joinCode
) {
    public static RaceSessionDTO fromEntity(RaceSession session) {
        return new RaceSessionDTO(
                session.getId(),
                session.getRace(),
                session.getJoinCode()
        );
    }
}
