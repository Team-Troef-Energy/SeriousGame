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
    private boolean isCompleted = false;

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

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted() {
        isCompleted = true;
    }

    public int calculateTotalCosts() {
        int totalCost = 0;
        for (Transformer transformer : this.getTransformers()) {
            totalCost += transformer.getBatteries().getAmount() * this.getCost().getBatteryCost();
            for (House house : transformer.getHouses()) {
                totalCost += house.getTotalSolarPanels() * this.getCost().getSolarPanelCost();
                totalCost += (house.getBattery() != null ? house.getBattery().getAmount() : 0) * this.getCost().getBatteryCost();
            }
        }
        return totalCost;
    }

    public float calculateTotalCO2() {
        float totalCO2 = 0;
        for (int hour = this.getStartTime(); hour <= this.getEndTime(); hour++) {
            for (Transformer transformer : this.getTransformers()) {
                for (House house : transformer.getHouses()) {
                    float consumption = house.getTotalConsumptionOfHour(hour);
                    float solarPanelOutput = house.getSolarPanelOutput(hour);
                    float netConsumption = consumption - solarPanelOutput;

                    if (netConsumption > 0 && house.getBattery() != null) {
                        Electricity electricity = house.getBattery().use(new Electricity(netConsumption, Direction.DEMAND));
                        netConsumption = electricity.amount();
                    }

                    if (netConsumption > 0) {
                        totalCO2 += netConsumption * this.getCost().getCO2Cost();
                    }
                }

                if (transformer.getBatteries() != null) {
                    Electricity electricity = transformer.getBatteries().use(new Electricity(totalCO2, Direction.DEMAND));
                    totalCO2 = electricity.amount();
                }
            }
        }
        return totalCO2;
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

    public Cost getCost() {
        return cost;
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
