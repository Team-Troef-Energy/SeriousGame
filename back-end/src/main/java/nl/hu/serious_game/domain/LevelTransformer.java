package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class LevelTransformer {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Congestion congestion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<LevelHouse> houses;

    @Setter
    private int maxBatteryCount = 4;

    public LevelTransformer(Congestion congestion, List<LevelHouse> houses, int maxBatteryCount) {
        this.congestion = congestion;
        this.houses = houses;
        this.maxBatteryCount = maxBatteryCount;
    }
}
