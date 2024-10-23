package nl.hu.serious_game.domain;

public enum Season {
    SPRING(0.89f, 1.25f),
    SUMMER(1f, 1f),
    AUTUMN(0.54f, 1.25f),
    WINTER(0.27f, 1.5f);

    private final float solarPanelFactor;
    private final float houseBaseConsumptionFactor;

    Season(float solarPanelFactor, float houseBaseConsumptionFactor) {
        this.solarPanelFactor = solarPanelFactor;
        this.houseBaseConsumptionFactor = houseBaseConsumptionFactor;
    }

    public float getSolarPanelFactor() {
        return solarPanelFactor;
    }

    public float getHouseBaseConsumptionFactor() {
        return houseBaseConsumptionFactor;
    }
}
