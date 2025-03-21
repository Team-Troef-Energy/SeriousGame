package nl.hu.serious_game.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Objective {
    private float maxCo2;
    private int maxCoins;

    public Objective(float maxCo2, int maxCoins) {
        if (maxCo2 < 0 || maxCoins < 0) {
            throw new IllegalArgumentException("maxCo2 and maxCoins must be positive");
        }
        this.maxCo2 = maxCo2;
        this.maxCoins = maxCoins;
    }
}
