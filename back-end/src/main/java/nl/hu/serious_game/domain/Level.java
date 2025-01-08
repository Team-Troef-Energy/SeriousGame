package nl.hu.serious_game.domain;

import nl.hu.serious_game.domain.exceptions.DoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class Level implements Cloneable {
    private Season season;
    private int startTime;
    private int endTime;
    private Objective objective;
    private List<Transformer> transformers = new ArrayList<>();
    private Cost cost;

    public Level(Season season, int startTime, int endTime, Objective objective, List<Transformer> transformers, Cost cost) {
        this.season = season;
        this.startTime = startTime;
        this.endTime = endTime;
        this.objective = objective;
        this.cost = cost;
        if (!transformers.isEmpty()) {
            this.transformers = transformers;
        } else {
            throw new IllegalArgumentException("transformers is empty");
        }
    }

    public void setHouseSolarPanels(int transformerId, int houseId, int amount) {
        transformers.stream().filter(transformer -> transformer.getId() == transformerId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setHouseSolarPanels(houseId, amount);
    }

    public void setHouseBattery(int transformerId, int houseId, int amount) {
        transformers.stream().filter(transformer -> transformer.getId() == transformerId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setHouseBattery(houseId, amount);
    }

    public void setTransformerBattery(int transformerId, int amount) {
        transformers.stream().filter(transformer -> transformer.getId() == transformerId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setBattery(amount);
    }

    public Objective getObjective() {
        return objective;
    }

    public List<Transformer> getTransformers() {
        return transformers;
    }

    public Season getSeason() {
        return season;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public Level clone() {
        try {
            Level clone = (Level) super.clone();
            clone.transformers = new ArrayList<>();
            for (Transformer transformer : this.transformers) {
                clone.transformers.add(transformer.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed");
        }
    }
}
