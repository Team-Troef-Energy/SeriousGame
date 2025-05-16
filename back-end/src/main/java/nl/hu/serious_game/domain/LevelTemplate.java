package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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

    private int startTime;
    private int endTime;

    @Setter
    private Objective objective;

    @Setter
    private Cost cost;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<LevelTransformer> transformers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'GLOBAL'")
    private LevelType type;

    @ManyToOne(optional = true)
    private Race race;

    public LevelTemplate(int levelNumber, Season season, int startTime, int endTime, Objective objective, List<LevelTransformer> transformers, Cost cost) {
        if (startTime < 0 || startTime > 23 || endTime < 0 || endTime > 23) {
            throw new IllegalArgumentException("startTime and endTime must be within 0 and 23");
        }

        this.levelNumber = levelNumber;
        this.season = season;
        this.startTime = startTime;
        this.endTime = endTime;
        this.objective = objective;
        this.transformers = transformers;
        this.cost = cost;

        for (LevelTransformer transformer : transformers) {
            transformer.setLevel(this);
        }
    }

    public void setStartTime(int startTime) {
        if (startTime < 0 || startTime > 23) {
            throw new IllegalArgumentException("startTime must be within 0 and 23");
        }

        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        if (endTime < 0 || endTime > 23) {
            throw new IllegalArgumentException("endTime must be within 0 and 23");
        }

        this.endTime = endTime;
    }
}
