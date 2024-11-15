package nl.hu.serious_game.domain;

import java.util.ArrayList;

public class House implements Cloneable {
    private int id;
    private float maxCurrent;
    private int totalSolarPanels;
    private ArrayList<Battery> batteries;
    private DayProfile dayProfile;
    private HouseOptions houseOptions;

    public House (int id, int totalSolarPanels, DayProfile dayProfile, HouseOptions houseOptions) {
        this.id = id;
        this.totalSolarPanels = totalSolarPanels;
        this.dayProfile = dayProfile;
        this.houseOptions = houseOptions;
    }

    public void addSolarPanel(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of solar panels");
        }
        totalSolarPanels += amount;
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

    public DayProfile getDayProfile() {
        return dayProfile;
    }

    public float getSolarPanelOutput(int hour) {
        return totalSolarPanels * dayProfile.getValue(hour, "SolarPanelProduction");
    }

    public float getBaseConsumption(int hour) {
        return dayProfile.getValue(hour, "HouseBaseConsumption");
    }

    public float getHeatPumpConsumption(int hour) {
        return dayProfile.getValue(hour, "HeatPumpConsumption");
    }

    public float getElectricVehicleConsumption(int hour) {
        return dayProfile.getValue(hour, "ElectricVehicleConsumption");
    }

    public Electricity getCurrent(int hour) {
        float production = getSolarPanelOutput(hour);
        float consumption = getTotalConsumptionOfHour(hour);
        if (production > consumption) {
            return new Electricity(production - consumption, Direction.PRODUCTION);
        } else {
            return new Electricity(consumption - production, Direction.DEMAND);
        }
    }

    private float getTotalConsumptionOfHour(int hour) {
        float total = getBaseConsumption(hour);
        total += houseOptions.hasHeatpump() ? getHeatPumpConsumption(hour) : 0;
        total += houseOptions.hasElectricVehicle() ? getElectricVehicleConsumption(hour) : 0;
        return total;
    }

    public int getTotalSolarPanels() {
        return totalSolarPanels;
    }

    @Override
    public House clone() {
        try {
            House clone = (House) super.clone();
            clone.batteries = new ArrayList<>();
            for (Battery battery : this.batteries) {
                clone.batteries.add(battery.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed");
        }
    }
}
