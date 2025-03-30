package nl.hu.serious_game.application.dto.out;

import nl.hu.serious_game.domain.LevelTemplate;
import nl.hu.serious_game.domain.Season;

import java.util.List;

public record LevelTemplateDTO(
        long id,
        int levelNumber,
        Season season,
        int startTime,
        int endTime,
        ObjectiveDTO objective,
        List<LevelTransformerDTO> transformers
) {
    public static LevelTemplateDTO fromEntity(LevelTemplate levelTemplate) {
        return new LevelTemplateDTO(
                levelTemplate.getId(),
                levelTemplate.getLevelNumber(),
                levelTemplate.getSeason(),
                levelTemplate.getStartTime(),
                levelTemplate.getEndTime(),
                new ObjectiveDTO(
                        levelTemplate.getObjective().getMaxCo2(),
                        levelTemplate.getObjective().getMaxCoins()
                ),
                levelTemplate.getTransformers().stream().map(levelTransformer -> new LevelTransformerDTO(
                        levelTransformer.getId(),
                        levelTransformer.getCongestion().isHasCongestion(),
                        levelTransformer.getCongestion().getMaxCurrent(),
                        levelTransformer.getMaxBatteryCount(),
                        levelTransformer.getHouses().stream().map(levelHouse -> new LevelHouseDTO(
                                levelHouse.getId(),
                                levelHouse.getHouseOptions().hasCongestion(),
                                levelHouse.getHouseOptions().maxCurrent(),
                                levelHouse.getHouseOptions().hasElectricVehicle(),
                                levelHouse.getHouseOptions().hasHeatPump(),
                                levelHouse.getHouseOptions().maxSolarPanelCount(),
                                levelHouse.getHouseOptions().maxBatteryCount()
                        )).toList()
                )).toList()
        );
    }
}
