package nl.hu.serious_game.domain;

public enum Season {
    // The power consumption data for solar panels is based on the summer season. So factors are used to adjust the data for the other seasons
    SPRING(0.89f, 1.25f, 1.19f),
    SUMMER(1f, 1f, 1f),
    AUTUMN(0.54f, 1.25f, 1.63f),
    WINTER(0.27f, 1.5f, 2f);

    private final float solarPanelFactor;
    private final float houseBaseConsumptionFactor;
    private final float HeatPumpFactor;

    Season(float solarPanelFactor, float houseBaseConsumptionFactor, float HeatPumpFactor) {
        this.solarPanelFactor = solarPanelFactor;
        this.houseBaseConsumptionFactor = houseBaseConsumptionFactor;
        this.HeatPumpFactor = HeatPumpFactor;
    }

    public float getSolarPanelFactor() {
        return solarPanelFactor;
    }

    public float getHouseBaseConsumptionFactor() {
        return houseBaseConsumptionFactor;
    }

    public float getHeatPumpConsumptionFactor() {
        return HeatPumpFactor;
    }
}
