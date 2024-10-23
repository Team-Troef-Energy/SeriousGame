package nl.hu.serious_game.domain;

import java.util.ArrayList;

public class House {
    private int id;
    private float maxCurrent;
    private boolean hasHeatpump;
    private boolean hasElectricVehicle;
    private float totalSolarpanels;
    private ArrayList<Battery> batteries;
    private DayProfile dayProfile;
}
