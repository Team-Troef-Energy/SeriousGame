package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.Direction;

public record CurrentDTO(
        Float amount,
        Direction direction
) {
}
