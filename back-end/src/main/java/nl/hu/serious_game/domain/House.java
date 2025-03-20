package nl.hu.serious_game.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Getter
@Entity
@NoArgsConstructor
public class House implements Cloneable {
    @Id
    @GeneratedValue
    @Setter // TODO: ids should not be settable. this is to set the id to zero when cloning.
    private int id;

    private int totalSolarPanels;

    @OneToOne(cascade = CascadeType.ALL)
    private Battery battery;
    private DayProfile dayProfile;
    private HouseOptions houseOptions;
    private Current excessCurrent;
    //private Integer hour;
    //private Current current;
    @Setter
    private float powerCost; // Cost in euros
    @Setter
    private float totalPowerCost; // Cost in euros

    public House(int totalSolarPanels, DayProfile dayProfile, HouseOptions houseOptions) {
        this.totalSolarPanels = totalSolarPanels;
        this.dayProfile = dayProfile;
        this.houseOptions = houseOptions;
        this.powerCost = 0;
    }

    public void addSolarPanel(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of solar panels");
        }

        if (amount > houseOptions.maxSolarPanelCount()) {
            throw new IllegalArgumentException("Cannot exceed the maximum number of solar panels: " + houseOptions.maxSolarPanelCount());
        }
        totalSolarPanels += amount;
    }

    void setTotalSolarPanels(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot set amount of solar panels to negative number");
        }

        if (amount > houseOptions.maxSolarPanelCount()) {
            throw new IllegalArgumentException("Cannot exceed the maximum number of solar panels: " + houseOptions.maxSolarPanelCount());
        }
        this.totalSolarPanels = amount;
    }

    public void removeSolarPanel(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot remove a negative amount of solar panels");
        }
        if (totalSolarPanels < amount) {
            throw new IllegalArgumentException("Cannot remove more solar panels than there are");
        }
        totalSolarPanels -= amount;
    }

    public void setBattery(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of batteries");
        }

        if (amount > houseOptions.maxBatteryCount()) {
            throw new IllegalArgumentException("Cannot exceed the maximum number of batteries: " + houseOptions.maxBatteryCount());
        }
        this.battery = new Battery(amount);
    }

    public float getSolarPanelConsumptionAtHour(int hour) {
        return totalSolarPanels * dayProfile.getValueFromColumnAtHour(hour, "SolarPanelProduction");
    }

    public float getBaseConsumptionAtHour(int hour) {
        return dayProfile.getValueFromColumnAtHour(hour, "HouseBaseConsumption");
    }

    public float getHeatPumpConsumptionAtHour(int hour) {
        return dayProfile.getValueFromColumnAtHour(hour, "HeatPumpConsumption");
    }

    public float getElectricVehicleConsumptionAtHour(int hour) {
        return dayProfile.getValueFromColumnAtHour(hour, "ElectricVehicleConsumption");
    }

    // Returns the current for the house at a specific hour
    public Current getCurrentAtHour(int hour) {
        return getCalculatedCurrentAtHour(hour);
        /*
        if (this.hour == null || hour == this.hour + 1) {
            return getCalculatedCurrentAtHour(hour);
        }
        if (hour == this.hour) {
            return this.current;
        }
        throw new IllegalArgumentException("Invalid hour");
         */
    }

    private Current getCalculatedCurrentAtHour(int hour) {
        float production = getSolarPanelConsumptionAtHour(hour);
        float consumption = getTotalConsumptionAtHour(hour);
        float amount;
        Direction direction;
        if (production > consumption) {
            amount = production - consumption;
            direction = Direction.PRODUCTION;
        } else {
            amount = consumption - production;
            direction = Direction.DEMAND;
        }
        Current current = new Current(amount, direction);

        if (battery != null) {
            current = battery.chargeOrDischarge(current);
        }

        if (houseOptions.hasCongestion() && current.getAmount() > houseOptions.maxCurrent()) {
            excessCurrent = new Current(current.getAmount() - houseOptions.maxCurrent(), direction);
            current = new Current(houseOptions.maxCurrent(), direction);
        } else {
            // Direction is not important here.
            excessCurrent = new Current(0f, direction);
        }
        return current;
    }

    public float getTotalConsumptionAtHour(int hour) {
        float total = getBaseConsumptionAtHour(hour);
        total += houseOptions.hasHeatPump() ? getHeatPumpConsumptionAtHour(hour) : 0;
        total += houseOptions.hasElectricVehicle() ? getElectricVehicleConsumptionAtHour(hour) : 0;
        return total;
    }

    @Override
    public House clone() {
        try {
            House clone = (House) super.clone();
            if (battery != null) {
                clone.battery = battery.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed");
        }
    }
}