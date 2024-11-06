package nl.hu.serious_game.domain;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private Season season;
    private int startTime;
    private int endTime;
    private Objective objective;
    private List<Transformer> transformers = new ArrayList<>();

    public Level(Season season, int startTime, int endTime, Objective objective, List<Transformer> transformers) {
        this.season = season;
        this.startTime = startTime;
        this.endTime = endTime;
        this.objective = objective;
        if (!transformers.isEmpty()) {
            this.transformers = transformers;
        } else {
            throw new IllegalArgumentException("transformers is empty");
        }
    }

    public Objective getObjective() {
        return objective;
    }

    public List<Transformer> getTransformers() {
        return transformers;
    }
}
