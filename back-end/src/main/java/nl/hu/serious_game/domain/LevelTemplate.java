package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor // for unit tests
public class LevelTemplate {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private int levelNumber;

    @Setter
    private Season season;
    @Setter
    private int startTime;
    @Setter
    private int endTime;
    @Setter
    private Objective objective;

    @Setter
    private Cost cost;

    @OneToMany(cascade = CascadeType.ALL)
    private List<LevelTransformer> transformers = new ArrayList<>();

    public LevelTemplate(int levelNumber, Season season, int startTime, int endTime, Objective objective, List<LevelTransformer> transformers, Cost cost) {
        this.levelNumber = levelNumber;
        this.season = season;
        this.startTime = startTime;
        this.endTime = endTime;
        this.objective = objective;
        this.transformers = transformers;
    }
}
