package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Race;
import nl.hu.serious_game.domain.RaceSession;
import nl.hu.serious_game.domain.RaceSessionUser;

import java.util.List;

public record RaceSessionDTO(
        Long id,
        Race race,
        String joinCode,
        List<RaceSessionUserDTO> users
) {
    public static RaceSessionDTO fromEntity(RaceSession session) {
        List<RaceSessionUserDTO> users = null;

        if (session.getUsers() != null) {
            users = session.getUsers().stream().map((RaceSessionUser sessionUser) -> RaceSessionUserDTO.fromEntity(sessionUser, false)).toList();
        }

        return new RaceSessionDTO(
                session.getId(),
                session.getRace(),
                session.getJoinCode(),
                users
        );
    }
}
