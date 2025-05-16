package nl.hu.serious_game.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@EqualsAndHashCode
@Getter
public class Cost {
    private int solarPanelCost; // Cost in coins
    private int batteryCost; // Cost in coins
    private float co2Cost; // 1 kWh of gray current equals 0.5 kg of CO2, so 1 kWh = 0.5 kg CO2

    // Default constructor for when no specific costs are given
    public Cost() {
        this.solarPanelCost = 0;
        this.batteryCost = 0;
        this.co2Cost = 0f;
    }

    public Cost(int solarPanelCost, int batteryCost, float co2Cost) {
        this.solarPanelCost = solarPanelCost;
        this.batteryCost = batteryCost;
        this.co2Cost = co2Cost;
    }
}
