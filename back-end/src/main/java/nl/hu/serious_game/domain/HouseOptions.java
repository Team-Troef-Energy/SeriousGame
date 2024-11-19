package nl.hu.serious_game.domain;

import java.util.Objects;

public class HouseOptions {
    private boolean hasHeatPump;
    private boolean hasElectricVehicle;

    public HouseOptions() {
        this.hasHeatPump = false;
        this.hasElectricVehicle = false;
    }

    public HouseOptions(boolean hasHeatPump, boolean hasElectricVehicle) {
        this.hasHeatPump = hasHeatPump;
        this.hasElectricVehicle = hasElectricVehicle;
    }

    public boolean hasHeatpump() {
        return hasHeatPump;
    }

    public boolean hasElectricVehicle() {
        return hasElectricVehicle;
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
