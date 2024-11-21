package nl.hu.serious_game.application.dto.in;

import java.util.List;

public record TransformerUpdateDTO(
        int id,
        List<HouseUpdateDTO> houses,
        int batteries
) {}

