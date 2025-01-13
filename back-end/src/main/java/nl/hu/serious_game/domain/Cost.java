package nl.hu.serious_game.domain;

import java.util.Objects;

public class Cost {
    private final int solarPanelCost;
    private final int batteryCost;
    private final float CO2Cost = 0.5f; // 1 kWh of gray electricity equals 0.5 kg of CO2

    // Default constructor for when no specific costs are given
    public Cost() {
        this.solarPanelCost = 0;
        this.batteryCost = 0;
    }

    public Cost(int solarPanelCost, int batteryCost) {
        this.solarPanelCost = solarPanelCost;
        this.batteryCost = batteryCost;
    }

    public int getSolarPanelCost() {
        return solarPanelCost;
    }

    public int getBatteryCost() {
        return batteryCost;
    }

    public float getCO2Cost() {
        return CO2Cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return solarPanelCost == cost.solarPanelCost && batteryCost == cost.batteryCost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(solarPanelCost, batteryCost);
    }
}
