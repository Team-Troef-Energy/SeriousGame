package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.HouseOptions;

public record HouseDTO(
        int id,
        CurrentDTO current,
        BatteryDTO batteries,
        int solarpanels,
        float production,
        float consumption,
        boolean hasCongestion,
        float maxCurrent) {

    public HouseDTO(int id, CurrentDTO current, BatteryDTO batteries, int solarpanels, float production, float consumption, HouseOptions houseOptions) {
        this(id, current, batteries, solarpanels, production, consumption, houseOptions.hasCongestion(), houseOptions.maxCurrent());
    }
}
