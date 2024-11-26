package nl.hu.serious_game.domain;

public class House implements Cloneable {
    private final int id;
    private float maxCurrent;
    private int totalSolarPanels;
    private Battery battery;
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

    void setTotalSolarPanels(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot set amount of solar panels to negative number");
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
        this.battery = new Battery(amount);
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
        float amount;
        Direction direction;
        if (production > consumption) {
            amount = production - consumption;
            direction = Direction.PRODUCTION;
        } else {
            amount = consumption - production;
            direction = Direction.DEMAND;
        }

        if (battery != null) {
            return battery.use(new Electricity(amount, direction));
        }
        return new Electricity(amount, direction);
    }

    public float getTotalConsumptionOfHour(int hour) {
        float total = getBaseConsumption(hour);
        total += houseOptions.hasHeatpump() ? getHeatPumpConsumption(hour) : 0;
        total += houseOptions.hasElectricVehicle() ? getElectricVehicleConsumption(hour) : 0;
        return total;
    }

    public int getTotalSolarPanels() {
        return totalSolarPanels;
    }

    public Battery getBattery() {
        return battery;
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
}
