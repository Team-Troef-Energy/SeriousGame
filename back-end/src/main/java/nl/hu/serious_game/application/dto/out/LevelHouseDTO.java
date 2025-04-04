package nl.hu.serious_game.application.dto.out;

public record LevelHouseDTO(
        long id,
        boolean hasCongestion,
        float maxCurrent,
        boolean hasElectricVehicle,
        boolean hasHeatpump,
        int maxSolarPanelCount,
        int maxBatteryCount) {
}
