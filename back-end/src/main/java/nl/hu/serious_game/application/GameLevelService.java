package nl.hu.serious_game.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.hu.serious_game.application.dto.in.GameLevelUpdateDTO;
import nl.hu.serious_game.application.dto.out.BatteryDTO;
import nl.hu.serious_game.application.dto.out.CurrentDTO;
import nl.hu.serious_game.application.dto.out.GameHouseDTO;
import nl.hu.serious_game.application.dto.out.GameLevelDTO;
import nl.hu.serious_game.application.dto.out.GameTransformerDTO;
import nl.hu.serious_game.application.dto.out.HourDTO;
import nl.hu.serious_game.application.dto.out.ObjectiveDTO;
import nl.hu.serious_game.data.GameLevelRepository;
import nl.hu.serious_game.data.LevelTemplateRepository;
import nl.hu.serious_game.domain.Current;
import nl.hu.serious_game.domain.GameHouse;
import nl.hu.serious_game.domain.GameLevel;
import nl.hu.serious_game.domain.GameTransformer;
import nl.hu.serious_game.domain.LevelTemplate;
import nl.hu.serious_game.domain.Season;

/// Game initialization and update logic.
/// The update logic is performed after every operation from the user.
@Service
public class GameLevelService {
    private final GameLevelRepository gameLevelRepository;
    private final LevelTemplateRepository levelTemplateRepository;

    @Autowired
    public GameLevelService(GameLevelRepository gameLevelRepository, LevelTemplateRepository levelTemplateRepository) {
        this.gameLevelRepository = gameLevelRepository;
        this.levelTemplateRepository = levelTemplateRepository;
    }

    public GameLevelDTO startGame(long levelTemplateId) {
        LevelTemplate levelTemplate = levelTemplateRepository.getLevelTemplateById(levelTemplateId).orElseThrow(() -> new IllegalArgumentException("Invalid level number"));

        GameLevel level = new GameLevel(
                levelTemplate,
                levelTemplate.getTransformers().stream().map(levelTransformer -> new GameTransformer(
                        levelTransformer,
                        levelTransformer.getHouses().stream().map(levelHouse -> new GameHouse(
                                levelHouse,
                                0
                        )).toList(),
                        0
                )).toList()
        );

        level = this.gameLevelRepository.save(level);

        return computeGameUpdate(level);
    }

    private ArrayList<GameHouseDTO> getHouseDTOS(GameTransformer transformer, int hour) {
        ArrayList<GameHouseDTO> gameHouseDTOS = new ArrayList<>();
        for (int houseIndex = 0; houseIndex < transformer.getHouses().size(); houseIndex++) { // Loop through each house
            GameHouse house = transformer.getHouses().get(houseIndex);
            long houseId = house.getId();
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
                house.getTemplate().getId(),
                currentDTO,
                batteryDTO,
                house.getPowerCost(),
                house.getTotalPowerCost(),
                house.getTotalSolarPanels(), // Get the total solar panels of the house
                house.getSolarPanelConsumptionAtHour(hour),
                house.getTotalConsumptionAtHour(hour),
                house.getTemplate().getHouseOptions()
            ));
        }
        return gameHouseDTOS; // Return the list of HouseDTOs
    }

    public GameLevelDTO updateGame(long gameLevelId, GameLevelUpdateDTO levelUpdateDTO) {
        GameLevel level = gameLevelRepository.getGameLevelById(gameLevelId).clone();

        levelUpdateDTO.transformers().forEach(transformer -> {
            level.setTransformerBattery(transformer.id(), transformer.batteries());

            transformer.houses().forEach(house -> {
                level.setHouseBattery(transformer.id(), house.id(), house.batteries());
                level.setHouseSolarPanels(transformer.id(), house.id(), house.solarpanels());
            });
        });

        checkLevelCompletion(level);

        GameLevelDTO result = computeGameUpdate(level);

        //this.levelRepository.save(level);

        return result;
    }

    /// Compute new current amounts based on new parameters
    private GameLevelDTO computeGameUpdate(GameLevel level) {
        List<HourDTO> hours = new ArrayList<>();
        for (int hour = level.getTemplate().getStartTime(); hour <= level.getTemplate().getEndTime(); hour++) { // Loop through each hour in the level
            List<GameTransformerDTO> gameTransformerDTOS = new ArrayList<>();
            for (int transformerIndex = 0; transformerIndex < level.getTransformers().size(); transformerIndex++) { // Loop through each transformer
                GameTransformer transformer = level.getTransformers().get(transformerIndex);
                long transformerId = transformer.getId();
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
                        transformer.getTemplate().getId(),
                        currentDTO,
                        transformer.getTemplate().getCongestion(),
                        gameHouseDTOS,
                        batteryDTO,
                        transformer.getTemplate().getMaxBatteryCount()
                ));
            }
            hours.add(new HourDTO(hour, gameTransformerDTOS));
        }
        Season season = level.getTemplate().getSeason();
        ObjectiveDTO objective = new ObjectiveDTO(level.getTemplate().getObjective().getMaxCo2(), level.getTemplate().getObjective().getMaxCoins());

        return new GameLevelDTO(
                level.getId(),
                level.getTemplate().getId(),
                hours,
                season,
                level.getTemplate().getStartTime(),
                level.getTemplate().getEndTime(),
                objective,
                level.getTemplate().getCost(),
                level.isCompleted(),
                level.getTotalCosts(),
                level.getTotalCO2()
        );
    }

    private void checkLevelCompletion(GameLevel level) {
        level.getCalculatedTotalCosts();
        level.getCalculatedTotalCO2();

        if (level.getTotalCosts() <= level.getTemplate().getObjective().getMaxCoins() && level.getTotalCO2() <= level.getTemplate().getObjective().getMaxCo2()) {
            level.setIsCompleted();
        }
    }
}
