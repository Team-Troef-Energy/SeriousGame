package nl.hu.serious_game.domain;

import lombok.Getter;

@Getter
public enum Season {
    // The power consumption data for solar panels is based on the summer season. So factors are used to adjust the data for the other seasons
    SPRING(0.89f, 1.25f, 1.19f),
    SUMMER(1f, 1f, 1f),
    AUTUMN(0.54f, 1.25f, 1.63f),
    WINTER(0.27f, 1.5f, 2f);

    private final float solarPanelFactor;
    private final float houseBaseConsumptionFactor;
    private final float HeatPumpConsumptionFactor;

    Season(float solarPanelFactor, float houseBaseConsumptionFactor, float HeatPumpConsumptionFactor) {
        this.solarPanelFactor = solarPanelFactor;
        this.houseBaseConsumptionFactor = houseBaseConsumptionFactor;
        this.HeatPumpConsumptionFactor = HeatPumpConsumptionFactor;
    }
}
