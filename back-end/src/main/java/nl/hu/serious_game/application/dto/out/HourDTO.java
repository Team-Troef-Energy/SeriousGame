package nl.hu.serious_game.application.dto.out;

import java.util.List;

public record HourDTO(
        int hour,
        List<TransformerDTO> transformers
) {
}
