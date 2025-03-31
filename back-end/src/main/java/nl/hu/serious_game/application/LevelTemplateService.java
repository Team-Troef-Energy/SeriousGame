package nl.hu.serious_game.application;

import jakarta.persistence.EntityNotFoundException;
import nl.hu.serious_game.application.dto.in.LevelTemplateCreateDTO;
import nl.hu.serious_game.application.dto.out.LevelHouseDTO;
import nl.hu.serious_game.application.dto.out.LevelTemplateDTO;
import nl.hu.serious_game.application.dto.in.LevelTemplateUpdateDTO;
import nl.hu.serious_game.application.dto.out.LevelTransformerDTO;
import nl.hu.serious_game.application.dto.out.ObjectiveDTO;
import nl.hu.serious_game.data.LevelTemplateRepository;
import nl.hu.serious_game.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelTemplateService {
    private final LevelTemplateRepository levelTemplateRepository;

    @Autowired
    public LevelTemplateService(LevelTemplateRepository levelTemplateRepository) {
        this.levelTemplateRepository = levelTemplateRepository;
    }

    public LevelTemplateDTO createLevel(LevelTemplateCreateDTO createLevel) {
        LevelTemplate levelTemplate = new LevelTemplate(
                createLevel.levelNumber(),
                createLevel.season(),
                createLevel.startTime(),
                createLevel.endTime(),
                new Objective(createLevel.objective().maxCO2(), createLevel.objective().maxCoins()),
                createLevel.transformers().stream().map(createTransformer -> new LevelTransformer(
                        createTransformer.congestion(),
                        createTransformer.houses().stream().map(createHouse -> new LevelHouse(
                                new DayProfile(createLevel.season()),
                                new HouseOptions(
                                        createHouse.hasHeatPump(),
                                        createHouse.hasElectricVehicle(),
                                        createHouse.congestion()
                                )
                        )).toList(),
                        createTransformer.maxBatteryCount()
                )).toList()
        );

        levelTemplate = levelTemplateRepository.save(levelTemplate);

        return LevelTemplateDTO.fromEntity(levelTemplate);
    }

    public LevelTemplateDTO updateLevel(long id, LevelTemplateUpdateDTO updateLevel) {
        LevelTemplate levelTemplate = this.levelTemplateRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        levelTemplate.setStartTime(updateLevel.startTime());
        levelTemplate.setEndTime(updateLevel.endTime());
        levelTemplate.setObjective(updateLevel.objective());
        levelTemplate.setSeason(updateLevel.season());

        levelTemplate.getTransformers().clear();
        levelTemplate.getTransformers().addAll(
                updateLevel.transformers().stream().map(updateTransformer -> new LevelTransformer(
                        updateTransformer.congestion(),
                        updateTransformer.houses().stream().map(updateHouse -> new LevelHouse(
                                new DayProfile(updateLevel.season()),
                                new HouseOptions(
                                        updateHouse.hasHeatPump(),
                                        updateHouse.hasElectricVehicle(),
                                        updateHouse.congestion()
                                )
                        )).toList(),
                        updateTransformer.maxBatteryCount()
                )).toList()
        );

        levelTemplate = this.levelTemplateRepository.save(levelTemplate);

        return LevelTemplateDTO.fromEntity(levelTemplate);
    }

    public List<LevelTemplateDTO> getAllLevels() {
        return this.levelTemplateRepository.findAll().stream().map(LevelTemplateDTO::fromEntity).toList();
    }
}
