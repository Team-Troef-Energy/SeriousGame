package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor // for unit tests
public class LevelTransformer {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Congestion congestion;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transformer")
    private List<LevelHouse> houses;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @Setter // being set by LevelTemplate's constructor.
    private LevelTemplate level;

    @Setter
    private int maxBatteryCount = 4;

    public LevelTransformer(Congestion congestion, List<LevelHouse> houses, int maxBatteryCount) {
        this.congestion = congestion;
        this.houses = houses;
        this.maxBatteryCount = maxBatteryCount;

        for (var house : houses) {
            house.setTransformer(this);
        }
    }

    public LevelTransformer(Congestion congestion, List<LevelHouse> houses, LevelTemplate levelTemplate, int maxBatteryCount) {
        this.congestion = congestion;
        this.houses = houses;
        this.level = levelTemplate;
        this.maxBatteryCount = maxBatteryCount;

        for (var house : houses) {
            house.setTransformer(this);
        }
    }

    public LevelTransformer(long id, Congestion congestion, List<LevelHouse> houses, int maxBatteryCount) {
        this.id = id;
        this.congestion = congestion;
        this.houses = houses;
        this.maxBatteryCount = maxBatteryCount;

        for (var house : houses) {
            house.setTransformer(this);
        }
    }
}
