package nl.hu.serious_game.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Current {
    private Float amount;
    private Direction direction;

    @Override
    public String toString() {
        return "Current[" +
                "amount=" + amount + ", " +
                "direction=" + direction + ']';
    }
}
