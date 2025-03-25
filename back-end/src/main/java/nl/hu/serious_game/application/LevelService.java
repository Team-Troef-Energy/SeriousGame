package nl.hu.serious_game.application;

import java.util.ArrayList;
import java.util.List;

import nl.hu.serious_game.data.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.hu.serious_game.Runner;
import nl.hu.serious_game.application.dto.in.LevelUpdateDTO;
import nl.hu.serious_game.application.dto.out.BatteryDTO;
import nl.hu.serious_game.application.dto.out.CurrentDTO;
import nl.hu.serious_game.application.dto.out.HourDTO;
import nl.hu.serious_game.application.dto.out.HouseDTO;
import nl.hu.serious_game.application.dto.out.LevelDTO;
import nl.hu.serious_game.application.dto.out.ObjectiveDTO;
import nl.hu.serious_game.application.dto.out.TransformerDTO;
import nl.hu.serious_game.domain.Current;
import nl.hu.serious_game.domain.House;
import nl.hu.serious_game.domain.Level;
import nl.hu.serious_game.domain.Season;
import nl.hu.serious_game.domain.Transformer;

@Service
public class LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public LevelDTO startLevel(long levelNumber) {
        if (levelNumber < 1 || levelNumber > levelRepository.getLevelCount()) {
            throw new IllegalArgumentException("Invalid level number");
        }
        Level level = levelRepository.getLevelById(levelNumber).clone();
        return runLevel(level);
    }


    private ArrayList<HouseDTO> getHouseDTOS(Transformer transformer, int hour) {
        ArrayList<HouseDTO> houseDTOs = new ArrayList<>();
        for (int houseIndex = 0; houseIndex < transformer.getHouses().size(); houseIndex++) { // Loop through each house
            House house = transformer.getHouses().get(houseIndex);
            int houseId = house.getId();
            Current current = house.getCurrentAtHour(hour); // Get the current for the house
            CurrentDTO currentDTO = new CurrentDTO(
                    current.getAmount(),
                    current.getDirection()
            );
            BatteryDTO batteryDTO = house.getBattery() != null
                ? new BatteryDTO(house.getBattery().getAmount(), house.getBattery().getCurrentCharge())
                : new BatteryDTO(0, 0);

            houseDTOs.add(new HouseDTO(
                houseId,
                currentDTO,
                batteryDTO,
                house.getPowerCost(),
                house.getTotalPowerCost(),
                house.getTotalSolarPanels(), // Get the total solar panels of the house
                house.getSolarPanelConsumptionAtHour(hour),
                house.getTotalConsumptionAtHour(hour),
                house.getHouseOptions()
            ));
        }
        return houseDTOs; // Return the list of HouseDTOs
    }

    public LevelDTO updateLevel(long levelNumber, LevelUpdateDTO levelUpdateDTO) {
        if (levelNumber < 1 || levelNumber > levelRepository.getLevelCount()) {
            throw new IllegalArgumentException("Invalid level number");
        }

        Level level = levelRepository.getLevelById(levelNumber).clone();

        levelUpdateDTO.transformers().forEach(transformer -> {
            level.setTransformerBattery(transformer.id(), transformer.batteries());

            transformer.houses().forEach(house -> {
                level.setHouseBattery(transformer.id(), house.id(), house.batteries());
                level.setHouseSolarPanels(transformer.id(), house.id(), house.solarpanels());
            });
        });

        checkLevelCompletion(level);

        LevelDTO result = runLevel(level);

        //this.levelRepository.save(level);

        return result;
    }

    private LevelDTO runLevel(Level level) {
        List<HourDTO> hours = new ArrayList<>();
        for (int hour = level.getStartTime(); hour <= level.getEndTime(); hour++) { // Loop through each hour in the level
            List<TransformerDTO> transformerDTOs = new ArrayList<>();
            for (int transformerIndex = 0; transformerIndex < level.getTransformers().size(); transformerIndex++) { // Loop through each transformer
                Transformer transformer = level.getTransformers().get(transformerIndex);
                int transformerId = transformer.getId();
                transformer.distributePowerCostAtHour(hour);

                ArrayList<HouseDTO> houseDTOs = getHouseDTOS(transformer, hour);

                Current current = transformer.getCalculatedLeftoverCurrentAtHour(hour);
                CurrentDTO currentDTO = new CurrentDTO(
                        current.getAmount(),
                        current.getDirection()
                );
                BatteryDTO batteryDTO = transformer.getBattery() != null
                        ? new BatteryDTO(transformer.getBattery().getAmount(), transformer.getBattery().getCurrentCharge())
                        : new BatteryDTO(0, 0);

                transformerDTOs.add(new TransformerDTO(
                        transformerId,
                        currentDTO,
                        transformer.getCongestion(),
                        houseDTOs,
                        batteryDTO,
                        transformer.getMaxBatteryCount()
                ));
            }
            hours.add(new HourDTO(hour, transformerDTOs));
        }
        Season season = level.getSeason();
        ObjectiveDTO objective = new ObjectiveDTO(level.getObjective().getMaxCo2(), level.getObjective().getMaxCoins());

        return new LevelDTO(hours, season, level.getStartTime(), level.getEndTime(), objective, level.getCost(), level.isCompleted(), level.getTotalCosts(), level.getTotalCO2()); // Return the LevelDTO
    }

    public int getTotalLevels() {
        return levelRepository.getLevelCount();
    }

    private void checkLevelCompletion(Level level) {
        level.getCalculatedTotalCosts();
        level.getCalculatedTotalCO2();

        if (level.getTotalCosts() <= level.getObjective().getMaxCoins() && level.getTotalCO2() <= level.getObjective().getMaxCo2()) {
            level.setIsCompleted();
        }
    }
}
