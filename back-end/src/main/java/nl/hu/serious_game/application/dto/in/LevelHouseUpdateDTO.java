package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.domain.Congestion;

public record LevelHouseUpdateDTO(
        long id,
        int maxBatteries,
        int masSolarpanels,
        Congestion congestion,
        boolean hasHeatPump,
        boolean hasElectricVehicle
) {}
