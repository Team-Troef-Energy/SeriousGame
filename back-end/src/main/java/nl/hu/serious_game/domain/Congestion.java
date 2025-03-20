package nl.hu.serious_game.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Records can't be @Embeddable.
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Congestion {
    private boolean hasCongestion;
    private float maxCurrent;

    @Override
    public String toString() {
        return "Congestion[" +
                "hasCongestion=" + hasCongestion + ", " +
                "maxCurrent=" + maxCurrent + ']';
    }
}
