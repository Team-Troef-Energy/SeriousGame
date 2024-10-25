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
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }

        return totalSolarPanels * dayProfile.getValue(hour, "SolarPanelProduction");
    }
}
