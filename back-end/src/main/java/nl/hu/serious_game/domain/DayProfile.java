package nl.hu.serious_game.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class DayProfile {
    private Season season;
    private static final Map<Integer, Map<String, Float>> hourData;

    static {
        hourData = new HashMap<>();
        initializeData();
    }

    private static void initializeData() {
        // The amount of electricity produced by the solar panels in kWh for a day
        // For example 0 kWh at 00:00, 0.017857143 kWh at 08:00, 0.160714286 kWh at 16:00, 0 kWh at 23:00
        float[] solarPanelProduction = {
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.017857143f, 0.107142857f, 0.160714286f, 0.178571429f,
                0.196428571f, 0.196428571f, 0.196428571f, 0.178571429f, 0.160714286f, 0.107142857f,
                0.017857143f, 0f, 0f, 0f, 0f, 0f
        };

        // The average amount of electricity consumed by a house in kWh for a day
        // For example 0.277 kWh at 00:00, 0.39 kWh at 08:00, 0.277 kWh at 23:00
        float[] houseBaseConsumption = {
                0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.277f, 0.39f, 0.39f, 0.39f, 0.39f,
                0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.39f, 0.277f
        };

        // The average amount of electricity consumed by a heat pump in kWh for a day
        float[] heatPumpConsumption = {
                0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f,
                0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f, 0.1825f,
                0.1825f, 0.1825f, 0.1825f, 0.1825f
        };

        // The average amount of electricity consumed by an electric vehicle in kWh for a day
        float[] electricVehicleConsumption = {
                0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f, 0f, 0f, 0f, 0f,
                0f, 0f, 0f, 0f, 0f, 0f, 0f, 0.36f, 0.36f, 0.36f, 0.36f, 0.36f
        };

        // The cost of electricity in euros per kWh for a day
        float[] powerCostData = {
                0.248f, 0.247f, 0.248f, 0.247f, 0.244f, 0.254f, 0.274f, 0.322f, 0.313f, 0.273f, 0.248f, 0.211f,
                0.160f, 0.154f, 0.153f, 0.154f, 0.196f, 0.249f, 0.286f, 0.311f, 0.285f, 0.261f, 0.253f, 0.243f
        };

        // The data hashmap stores electricity production, consumption, and cost for a specific instance at each hour of the day
        // The key is the specific instance you want the data for
        // The value is the hour of the day (0-23)
        for (int hour = 0; hour < 24; hour++) {
            Map<String, Float> data = new HashMap<>();
            data.put("SolarPanelProduction", solarPanelProduction[hour]);
            data.put("HouseBaseConsumption", houseBaseConsumption[hour]);
            data.put("HeatPumpConsumption", heatPumpConsumption[hour]);
            data.put("ElectricVehicleConsumption", electricVehicleConsumption[hour]);
            data.put("PowerCost", powerCostData[hour]);
            hourData.put(hour, data);
        }
    }

    // Returns the kWh or euro value of a specific instance for a specific hour
    // The instance can be "SolarPanelProduction", "HouseBaseConsumption", "HeatPumpConsumption", "ElectricVehicleConsumption", or "PowerCost"
    public float getValueFromColumnAtHour(int hour, String column) {
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
                case "ElectricVehicleConsumption", "PowerCost" -> {
                    return value;
                }
            }

            return value;
        }
        throw new IllegalArgumentException("Invalid hour or column");
    }
}
