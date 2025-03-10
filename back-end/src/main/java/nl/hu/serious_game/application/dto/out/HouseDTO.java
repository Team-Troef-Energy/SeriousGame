package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.HouseOptions;

public record HouseDTO(
        int id,
        CurrentDTO current,
        BatteryDTO batteries,
        float powerCost,
        float totalPowerCost,
        int solarpanels,
        float production,
        float consumption,
        boolean hasCongestion,
        float maxCurrent,
        boolean hasElectricVehicle,
        boolean hasHeatpump,
        int maxSolarPanelCount,
        int maxBatteryCount) {

    public HouseDTO(int id, CurrentDTO current, BatteryDTO batteries, float powerCost, float totalPowerCost, int solarpanels, float production, float consumption, HouseOptions houseOptions) {
        this(id, current, batteries, powerCost, totalPowerCost, solarpanels, production, consumption, houseOptions.hasCongestion(), houseOptions.maxCurrent(), houseOptions.hasElectricVehicle(), houseOptions.hasHeatPump(), houseOptions.maxSolarPanelCount(), houseOptions.maxBatteryCount());
    }
}
