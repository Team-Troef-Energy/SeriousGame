package nl.hu.serious_game.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.hu.serious_game.domain.exceptions.DoesNotExistException;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor // for unit tests
public class GameLevel implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LevelTemplate template;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "level")
    private List<GameTransformer> transformers = new ArrayList<>();
    private boolean isCompleted = false;
    private int totalCosts = 0; // Cost in coins
    private float totalCO2 = 0;

    public GameLevel(LevelTemplate template, List<GameTransformer> transformers) {
        this.template = template;
        if (!transformers.isEmpty()) {
            this.transformers = transformers;
        } else {
            throw new IllegalArgumentException("transformers is empty");
        }

        for (GameTransformer transformer : transformers) {
            transformer.setLevel(this);
        }
    }

    public void setHouseSolarPanels(long transformerId, long houseId, int amount) {
        transformers.stream().filter(transformer -> transformer.getId() == transformerId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setHouseSolarPanels(houseId, amount);
    }

    public void setHouseBattery(long transformerId, long houseId, int amount) {
        transformers.stream().filter(transformer -> transformer.getId() == transformerId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setHouseBattery(houseId, amount);
    }

    public void setTransformerBattery(long transformerId, int amount) {
        transformers.stream().filter(transformer -> transformer.getId() == transformerId)
                .findFirst().orElseThrow(DoesNotExistException::new)
                .setBattery(amount);
    }

    public void setIsCompleted() {
        isCompleted = true;
    }

    public int getCalculatedTotalCosts() {
        totalCosts = 0;
        for (GameTransformer transformer : this.getTransformers()) {
            totalCosts += transformer.getBattery().getAmount() * this.getTemplate().getCost().getBatteryCost();
            for (GameHouse house : transformer.getHouses()) {
                totalCosts += (house.getTotalSolarPanels() != 0 ? house.getTotalSolarPanels() : 0) * this.getTemplate().getCost().getSolarPanelCost();
                totalCosts += (house.getBattery() != null ? house.getBattery().getAmount() : 0) * this.getTemplate().getCost().getBatteryCost();
            }
        }
        return totalCosts;
    }

    public float getCalculatedTotalCO2() {
        totalCO2 = 0;
        for (int hour = this.template.getStartTime(); hour <= this.template.getEndTime(); hour++) {
            for (GameTransformer transformer : this.getTransformers()) {
                for (GameHouse house : transformer.getHouses()) {
                    float consumption = house.getTotalConsumptionAtHour(hour);
                    float solarPanelOutput = house.getSolarPanelConsumptionAtHour(hour);
                    float netConsumption = consumption - solarPanelOutput;

                    if (netConsumption > 0 && house.getBattery() != null) {
                        Current current = house.getBattery().chargeOrDischarge(new Current(netConsumption, Direction.DEMAND));
                        netConsumption = current.getAmount();
                    }

                    if (netConsumption > 0) {
                        totalCO2 += netConsumption * this.getTemplate().getCost().getCo2Cost();
                    }
                }

                if (transformer.getBattery() != null) {
                    Current current = transformer.getBattery().chargeOrDischarge(new Current(totalCO2, Direction.DEMAND));
                    totalCO2 = current.getAmount();
                }
            }
        }
        return totalCO2;
    }

    // The Level can be cloned to reuse the same structure with different values, avoiding the need to create a new instance each time
    public GameLevel clone() {
        try {
            GameLevel clone = (GameLevel) super.clone();
            clone.transformers = new ArrayList<>();
            for (GameTransformer transformer : this.transformers) {
                clone.transformers.add(transformer.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed");
        }
    }
}
