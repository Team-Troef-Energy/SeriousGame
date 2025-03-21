package nl.hu.serious_game.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@EqualsAndHashCode
@Getter
public class Cost {
    private int solarPanelCost; // Cost in coins
    private int batteryCost; // Cost in coins
    private final float CO2Cost = 0.5f; // 1 kWh of gray current equals 0.5 kg of CO2, so 1 kWh = 0.5 kg CO2

    // Default constructor for when no specific costs are given
    public Cost() {
        this.solarPanelCost = 0;
        this.batteryCost = 0;
    }

    public Cost(int solarPanelCost, int batteryCost) {
        this.solarPanelCost = solarPanelCost;
        this.batteryCost = batteryCost;
    }
}
