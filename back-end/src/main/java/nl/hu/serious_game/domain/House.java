package nl.hu.serious_game.domain;

import java.util.ArrayList;

public class House {
    private int id;
    private float maxCurrent;
    private boolean hasHeatpump;
    private boolean hasElectricVehicle;
    private int totalSolarPanels;
    private ArrayList<Battery> batteries;
    private DayProfile dayProfile;

    public House (int id, int totalSolarPanels, DayProfile dayProfile) {
        this.id = id;
        this.totalSolarPanels = totalSolarPanels;
        this.dayProfile = dayProfile;
    }

    public DayProfile getDayProfile() {
        return dayProfile;
    }

    public float getSolarPanelOutput(int hour) {
        return totalSolarPanels * dayProfile.getValue(hour, "SolarPanelProduction");
    }

    public float getConsumption(int hour) {
        return dayProfile.getValue(hour, "HouseBaseConsumption");
    }

    public Electricity getCurrent(int hour) {
        float production = getSolarPanelOutput(hour);
        float consumption = getConsumption(hour);
        if (production > consumption) {
            return new Electricity(production - consumption, Direction.PRODUCTION);
        } else {
            return new Electricity(consumption - production, Direction.DEMAND);
        }
    }
}
