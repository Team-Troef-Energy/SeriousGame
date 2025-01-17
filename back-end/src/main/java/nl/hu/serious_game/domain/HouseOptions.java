package nl.hu.serious_game.domain;

import java.util.Objects;

public class HouseOptions {
    private boolean hasHeatPump;
    private boolean hasElectricVehicle;
    private Congestion congestion;
    private int maxSolarPanelCount = 12;
    private int maxBatteryCount = 2;

    public HouseOptions() {
        this.hasHeatPump = false;
        this.hasElectricVehicle = false;
        this.congestion = new Congestion(false, 0f);
    }

    public HouseOptions(boolean hasHeatPump, boolean hasElectricVehicle) {
        this.hasHeatPump = hasHeatPump;
        this.hasElectricVehicle = hasElectricVehicle;
        this.congestion = new Congestion(false, 0f);
    }

    public HouseOptions(boolean hasHeatPump, boolean hasElectricVehicle, Congestion congestion) {
        this.hasHeatPump = hasHeatPump;
        this.hasElectricVehicle = hasElectricVehicle;
        this.congestion = congestion;
    }

    public boolean hasHeatpump() {
        return hasHeatPump;
    }

    public boolean hasElectricVehicle() {
        return hasElectricVehicle;
    }

    public boolean hasCongestion() {
        return congestion.hasCongestion();
    }

    public float maxCurrent() {
        return congestion.maxCurrent();
    }

    public int maxSolarPanelCount() {
        return maxSolarPanelCount;
    }

    public int maxBatteryCount() {
        return maxBatteryCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseOptions that = (HouseOptions) o;
        return hasHeatPump == that.hasHeatPump && hasElectricVehicle == that.hasElectricVehicle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasHeatPump, hasElectricVehicle);
    }
}
