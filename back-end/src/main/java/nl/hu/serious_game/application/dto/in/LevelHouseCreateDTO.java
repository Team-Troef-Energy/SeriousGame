package nl.hu.serious_game.application.dto.in;

import nl.hu.serious_game.domain.Congestion;

public record LevelHouseCreateDTO(
        int maxBatteries,
        int maxSolarPanels,
        Congestion congestion,
        boolean hasHeatPump,
        boolean hasElectricVehicle
) {}
