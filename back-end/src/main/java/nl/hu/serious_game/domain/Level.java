package nl.hu.serious_game.domain;

import java.util.ArrayList;

public class Level {
    private Season season;
    private int startTime;
    private int endTime;
    private Objective objective;
    private ArrayList<Transformer> transformers;

    public Level(Season season, int startTime, int endTime, Objective objective, ArrayList<Transformer> transformers) {
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

}
