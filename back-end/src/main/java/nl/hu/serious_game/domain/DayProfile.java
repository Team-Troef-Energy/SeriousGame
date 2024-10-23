package nl.hu.serious_game.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class DayProfile {
    private Season season;
    private Map<Integer, Map<String, Double>> hourData;

    public DayProfile(Season season) {
        this.season = season;
        hourData = new HashMap<>();
        initializeData();
    }

    private void initializeData() {
        double[] solarPanelProduction = {
                0, 0, 0, 0, 0, 0, 0, 0, 0.017857143, 0.107142857, 0.160714286, 0.178571429,
                0.196428571, 0.196428571, 0.196428571, 0.178571429, 0.160714286, 0.107142857,
                0.017857143, 0, 0, 0, 0, 0
        };

        double[] houseBaseConsumption = {
                0.277, 0.277, 0.277, 0.277, 0.277, 0.277, 0.277, 0.277, 0.39, 0.39, 0.39, 0.39,
                0.39, 0.39, 0.39, 0.39, 0.39, 0.39, 0.39, 0.39, 0.39, 0.39, 0.39, 0.277
        };

        for (int hour = 0; hour < 24; hour++) {
            Map<String, Double> data = new HashMap<>();
            data.put("SolarPanelProduction", solarPanelProduction[hour]);
            data.put("HouseBaseConsumption", houseBaseConsumption[hour]);
            hourData.put(hour, data);
        }
    }

    public double getValue(int hour, String column) {
        if (hourData.containsKey(hour) && hourData.get(hour).containsKey(column)) {
            double value = hourData.get(hour).get(column);

            // Apply relevant season factors for each column
            if (column.equals("SolarPanelProduction")) {
                value *= season.getSolarPanelFactor();
            } else if (column.equals("HouseBaseConsumption")) {
                value *= season.getHouseBaseConsumptionFactor();
            }

            // Round to 4 decimals
            BigDecimal roundedValue = new BigDecimal(value).setScale(4, RoundingMode.HALF_UP);
            return roundedValue.doubleValue();
        }
        throw new IllegalArgumentException("Invalid hour or column");
    }
}