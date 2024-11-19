package nl.hu.serious_game.application;

import nl.hu.serious_game.Runner;
import nl.hu.serious_game.application.dto.out.*;
import nl.hu.serious_game.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LevelService {

    private final Runner runner;

    @Autowired
    public LevelService(Runner runner) {
        this.runner = runner;
    }

    public LevelDTO startLevel(int levelNumber) {
        Level level;
        switch (levelNumber) {
            case 1:
                level = runner.getLevel1();
                break;
            default:
                throw new IllegalArgumentException("Invalid level number");
        }

        List<HourDTO> hours = new ArrayList<>();
        for (int hour = level.getStartTime(); hour <= level.getEndTime(); hour++) { // Loop through each hour in the level
            List<TransformerDTO> transformerDTOs = new ArrayList<>();
            for (int transformerId = 0; transformerId < level.getTransformers().size(); transformerId++) { // Loop through each transformer

                Transformer transformer = level.getTransformers().get(transformerId);

                ArrayList<HouseDTO> houseDTOs = getHouseDTOS(transformer, hour);

                Electricity electricity = transformer.getLeftoverCurrent(hour);
                CurrentDTO current = new CurrentDTO(
                        electricity.amount(),
                        electricity.direction()
                );
                BatteryDTO batteryDTO = transformer.getBatteries() != null
                    ? new BatteryDTO(transformer.getBatteries().getAmount(), transformer.getBatteries().getCurrentCharge())
                    : new BatteryDTO(0, 0);

                transformerDTOs.add(new TransformerDTO(
                    transformerId, // Use the index as the transformer ID
                    current,
                    houseDTOs,
                    batteryDTO
                ));
            }
            hours.add(new HourDTO(hour, transformerDTOs));
        }
        Season season = level.getSeason();
        ObjectiveDTO objective = new ObjectiveDTO(level.getObjective().getMaxCo2(), level.getObjective().getMaxCoins());

        return new LevelDTO(hours, season, level.getStartTime(), level.getEndTime(), objective); // Return the LevelDTO
    }


    private ArrayList<HouseDTO> getHouseDTOS(Transformer transformer, int hour) {
        ArrayList<HouseDTO> houseDTOs = new ArrayList<>();
        for (int houseIndex = 0; houseIndex < transformer.getHouses().size(); houseIndex++) { // Loop through each house
            House house = transformer.getHouses().get(houseIndex);
            Electricity electricity = house.getCurrent(hour); // Get the current for the house
            CurrentDTO currentDTO = new CurrentDTO(
                    electricity.amount(),
                    electricity.direction()
            );
            BatteryDTO batteryDTO = house.getBattery() != null
                ? new BatteryDTO(house.getBattery().getAmount(), house.getBattery().getCurrentCharge())
                : new BatteryDTO(0, 0);

            houseDTOs.add(new HouseDTO(
                houseIndex, // Use the index as the house ID
                currentDTO,
                batteryDTO,
                house.getTotalSolarPanels() // Get the total solar panels of the house
            ));
        }
        return houseDTOs; // Return the list of HouseDTOs
    }
}