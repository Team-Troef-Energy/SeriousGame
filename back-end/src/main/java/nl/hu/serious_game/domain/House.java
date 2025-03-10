package nl.hu.serious_game.domain;

public class House implements Cloneable {
    private final int id;
    private int totalSolarPanels;
    private Battery battery;
    private DayProfile dayProfile;
    private HouseOptions houseOptions;
    private Current excessCurrent;
    private Integer hour;
    private Current current;
    private float powerCost; // Cost in euros
    private float totalPowerCost; // Cost in euros

    public House (int id, int totalSolarPanels, DayProfile dayProfile, HouseOptions houseOptions) {
        this.id = id;
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

    public DayProfile getDayProfile() {
        return dayProfile;
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
        if (this.hour == null || hour == this.hour + 1) {
            return getCalculatedCurrentAtHour(hour);
        }
        if (hour == this.hour) {
            return this.current;
        }
        throw new IllegalArgumentException("Invalid hour");
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

        if (houseOptions.hasCongestion() && current.amount() > houseOptions.maxCurrent()) {
            excessCurrent = new Current(current.amount() - houseOptions.maxCurrent(), direction);
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

    public int getTotalSolarPanels() {
        return totalSolarPanels;
    }

    public Battery getBattery() {
        return battery;
    }

    public Current getExcessCurrent() {
        return excessCurrent;
    }

    public float getPowerCost() {
        return powerCost;
    }

    public void setPowerCost(float powerCost) {
        this.powerCost = powerCost;
    }

    public void setTotalPowerCost(float totalPowerCost) {
        this.totalPowerCost = totalPowerCost;
    }

    public float getTotalPowerCost() {
        return totalPowerCost;
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

    public int getId() {
        return id;
    }

    public HouseOptions getHouseOptions() {
        return houseOptions;
    }
}