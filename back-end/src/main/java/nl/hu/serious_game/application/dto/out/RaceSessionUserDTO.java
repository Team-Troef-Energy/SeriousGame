package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.RaceSessionUser;

public record RaceSessionUserDTO(
        Long raceId,
        RaceDTO race,
        String username,
        String token
) {
    public static RaceSessionUserDTO fromEntity(RaceSessionUser sessionUser, boolean includeToken) {
        return new RaceSessionUserDTO(
                sessionUser.getRaceSession().getRace().getId(),
                RaceDTO.fromEntity(sessionUser.getRaceSession().getRace()),
                sessionUser.getUsername(),
                includeToken ? sessionUser.getToken() : null
        );
    }
}
