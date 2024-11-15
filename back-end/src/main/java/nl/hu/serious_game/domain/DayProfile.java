package nl.hu.serious_game.domain;

import java.util.HashMap;
import java.util.Map;

public class DayProfile {
    private Season season;
    private Map<Integer, Map<String, Float>> hourData;

    public DayProfile(Season season) {
        this.season = season;
        hourData = new HashMap<>();
        initializeData();
    }

    private void initializeData() {
        float[] solarPanelProduction = {
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.017857143f, 0.107142857f, 0.160714286f, 0.178571429f,
                0.196428571f, 0.196428571f, 0.196428571f, 0.178571429f, 0.160714286f, 0.107142857f,
                0.017857143f, 0f, 0f, 0f, 0f, 0f
        };

        float[] houseBaseConsumption = {
                0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.39f, 0.39f, 0.39f, 0.39f,
                0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.277f
        };

        float[] heatPumpConsumption = {
                4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f,
                4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f, 4.38f
        };

        float[] electricVehicleConsumption = {
                0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f
        };

        for (int hour = 0; hour < 24; hour++) {
            Map<String, Float> data = new HashMap<>();
            data.put("SolarPanelProduction", solarPanelProduction[hour]);
            data.put("HouseBaseConsumption", houseBaseConsumption[hour]);
            data.put("HeatPumpConsumption", heatPumpConsumption[hour]);
            data.put("ElectricVehicleConsumptionHour", electricVehicleConsumption[hour]);
            hourData.put(hour, data);
        }
    }

    public float getValue(int hour, String column) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        if (hourData.containsKey(hour) && hourData.get(hour).containsKey(column)) {
            float value = hourData.get(hour).get(column);

            // Apply relevant season factors for each column
            switch (column) {
                case "SolarPanelProduction" -> value *= season.getSolarPanelFactor();
                case "HouseBaseConsumption" -> value *= season.getHouseBaseConsumptionFactor();
                case "HeatPumpConsumption" -> value *= season.getHeatPumpConsumptionFactor();
                case "ElectricVehicleConsumption" -> {
                    return value;
                }
            }

            return value;
        }
        throw new IllegalArgumentException("Invalid hour or column");
    }
}