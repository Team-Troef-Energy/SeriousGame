package nl.hu.serious_game.domain;

public enum Season {
    SPRING(0.89, 1.25),
    SUMMER(1, 1),
    AUTUMN(0.54, 1.25),
    WINTER(0.27, 1.5);

    private final double solarPanelFactor;
    private final double houseBaseConsumptionFactor;

    Season(double solarPanelFactor, double houseBaseConsumptionFactor) {
        this.solarPanelFactor = solarPanelFactor;
        this.houseBaseConsumptionFactor = houseBaseConsumptionFactor;
    }

    public double getSolarPanelFactor() {
        return solarPanelFactor;
    }

    public double getHouseBaseConsumptionFactor() {
        return houseBaseConsumptionFactor;
    }
}
