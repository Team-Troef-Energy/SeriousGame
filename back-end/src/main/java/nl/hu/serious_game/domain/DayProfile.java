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

        for (int hour = 0; hour < 24; hour++) {
            Map<String, Float> data = new HashMap<>();
            data.put("SolarPanelProduction", solarPanelProduction[hour]);
            data.put("HouseBaseConsumption", houseBaseConsumption[hour]);
            hourData.put(hour, data);
        }
    }

    public float getValue(int hour, String column) {
        if (hourData.containsKey(hour) && hourData.get(hour).containsKey(column)) {
            float value = hourData.get(hour).get(column);

            // Apply relevant season factors for each column
            if (column.equals("SolarPanelProduction")) {
                value *= season.getSolarPanelFactor();
            } else if (column.equals("HouseBaseConsumption")) {
                value *= season.getHouseBaseConsumptionFactor();
            }

            return value;
        }
        throw new IllegalArgumentException("Invalid hour or column");
    }
}