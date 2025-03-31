package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.domain.Congestion;

public record GameHouseCreateDTO(
        int maxBatteries,
        int masSolarpanels,
        boolean hasElectricVehicle,
        boolean hasHeatpump,
        Congestion congestion
) {}
