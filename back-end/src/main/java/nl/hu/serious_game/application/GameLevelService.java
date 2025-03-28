package nl.hu.serious_game.application;

import java.util.ArrayList;
import java.util.List;

import nl.hu.serious_game.data.GameLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.hu.serious_game.application.dto.in.LevelUpdateDTO;
import nl.hu.serious_game.application.dto.out.BatteryDTO;
import nl.hu.serious_game.application.dto.out.CurrentDTO;
import nl.hu.serious_game.application.dto.out.HourDTO;
import nl.hu.serious_game.application.dto.out.GameHouseDTO;
import nl.hu.serious_game.application.dto.out.GameLevelDTO;
import nl.hu.serious_game.application.dto.out.ObjectiveDTO;
import nl.hu.serious_game.application.dto.out.GameTransformerDTO;
import nl.hu.serious_game.domain.Current;
import nl.hu.serious_game.domain.GameHouse;
import nl.hu.serious_game.domain.GameLevel;
import nl.hu.serious_game.domain.Season;
import nl.hu.serious_game.domain.GameTransformer;

@Service
public class GameLevelService {
    private final GameLevelRepository gameLevelRepository;

    @Autowired
    public GameLevelService(GameLevelRepository gameLevelRepository) {
        this.gameLevelRepository = gameLevelRepository;
    }

    public GameLevelDTO startLevel(long levelNumber) {
        if (levelNumber < 1 || levelNumber > gameLevelRepository.getLevelCount()) {
            throw new IllegalArgumentException("Invalid level number");
        }
        GameLevel level = gameLevelRepository.getGameLevelById(levelNumber).clone();
        return runLevel(level);
    }


    private ArrayList<GameHouseDTO> getHouseDTOS(GameTransformer transformer, int hour) {
        ArrayList<GameHouseDTO> gameHouseDTOS = new ArrayList<>();
        for (int houseIndex = 0; houseIndex < transformer.getHouses().size(); houseIndex++) { // Loop through each house
            GameHouse house = transformer.getHouses().get(houseIndex);
            int houseId = house.getId();
            Current current = house.getCurrentAtHour(hour); // Get the current for the house
            CurrentDTO currentDTO = new CurrentDTO(
                    current.getAmount(),
                    current.getDirection()
            );
            BatteryDTO batteryDTO = house.getBattery() != null
                ? new BatteryDTO(house.getBattery().getAmount(), house.getBattery().getCurrentCharge())
                : new BatteryDTO(0, 0);

            gameHouseDTOS.add(new GameHouseDTO(
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
        return gameHouseDTOS; // Return the list of HouseDTOs
    }

    public GameLevelDTO updateLevel(long levelNumber, LevelUpdateDTO levelUpdateDTO) {
        if (levelNumber < 1 || levelNumber > gameLevelRepository.getLevelCount()) {
            throw new IllegalArgumentException("Invalid level number");
        }

        GameLevel level = gameLevelRepository.getGameLevelById(levelNumber).clone();

        levelUpdateDTO.transformers().forEach(transformer -> {
            level.setTransformerBattery(transformer.id(), transformer.batteries());

            transformer.houses().forEach(house -> {
                level.setHouseBattery(transformer.id(), house.id(), house.batteries());
                level.setHouseSolarPanels(transformer.id(), house.id(), house.solarpanels());
            });
        });

        checkLevelCompletion(level);

        GameLevelDTO result = runLevel(level);

        //this.levelRepository.save(level);

        return result;
    }

    private GameLevelDTO runLevel(GameLevel level) {
        List<HourDTO> hours = new ArrayList<>();
        for (int hour = level.getStartTime(); hour <= level.getEndTime(); hour++) { // Loop through each hour in the level
            List<GameTransformerDTO> gameTransformerDTOS = new ArrayList<>();
            for (int transformerIndex = 0; transformerIndex < level.getTransformers().size(); transformerIndex++) { // Loop through each transformer
                GameTransformer transformer = level.getTransformers().get(transformerIndex);
                int transformerId = transformer.getId();
                transformer.distributePowerCostAtHour(hour);

                ArrayList<GameHouseDTO> gameHouseDTOS = getHouseDTOS(transformer, hour);

                Current current = transformer.getCalculatedLeftoverCurrentAtHour(hour);
                CurrentDTO currentDTO = new CurrentDTO(
                        current.getAmount(),
                        current.getDirection()
                );
                BatteryDTO batteryDTO = transformer.getBattery() != null
                        ? new BatteryDTO(transformer.getBattery().getAmount(), transformer.getBattery().getCurrentCharge())
                        : new BatteryDTO(0, 0);

                gameTransformerDTOS.add(new GameTransformerDTO(
                        transformerId,
                        currentDTO,
                        transformer.getCongestion(),
                        gameHouseDTOS,
                        batteryDTO,
                        transformer.getMaxBatteryCount()
                ));
            }
            hours.add(new HourDTO(hour, gameTransformerDTOS));
        }
        Season season = level.getSeason();
        ObjectiveDTO objective = new ObjectiveDTO(level.getObjective().getMaxCo2(), level.getObjective().getMaxCoins());

        return new GameLevelDTO(hours, season, level.getStartTime(), level.getEndTime(), objective, level.getCost(), level.isCompleted(), level.getTotalCosts(), level.getTotalCO2()); // Return the LevelDTO
    }

    public int getTotalLevels() {
        return gameLevelRepository.getLevelCount();
    }

    private void checkLevelCompletion(GameLevel level) {
        level.getCalculatedTotalCosts();
        level.getCalculatedTotalCO2();

        if (level.getTotalCosts() <= level.getObjective().getMaxCoins() && level.getTotalCO2() <= level.getObjective().getMaxCo2()) {
            level.setIsCompleted();
        }
    }
}
