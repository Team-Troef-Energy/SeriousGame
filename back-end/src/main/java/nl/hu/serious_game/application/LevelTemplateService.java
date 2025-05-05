package nl.hu.serious_game.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import nl.hu.serious_game.application.dto.in.LevelTemplateCreateDTO;
import nl.hu.serious_game.application.dto.in.LevelTemplateUpdateDTO;
import nl.hu.serious_game.application.dto.out.LevelTemplateDTO;
import nl.hu.serious_game.data.LevelTemplateRepository;
import nl.hu.serious_game.domain.DayProfile;
import nl.hu.serious_game.domain.HouseOptions;
import nl.hu.serious_game.domain.LevelHouse;
import nl.hu.serious_game.domain.LevelTemplate;
import nl.hu.serious_game.domain.LevelTransformer;
import nl.hu.serious_game.domain.Objective;

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
                                        createHouse.congestion(),
                                        createHouse.maxSolarPanels(),
                                        createHouse.maxBatteries()
                                )
                        )).toList(),
                        createTransformer.maxBatteryCount()
                )).toList(),
                createLevel.cost()
        );

        levelTemplate = levelTemplateRepository.save(levelTemplate);

        return LevelTemplateDTO.fromEntity(levelTemplate);
    }

    public LevelTemplateDTO updateLevel(long id, LevelTemplateUpdateDTO updateLevel) {
        final LevelTemplate levelTemplate = this.levelTemplateRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        levelTemplate.setStartTime(updateLevel.startTime());
        levelTemplate.setEndTime(updateLevel.endTime());
        levelTemplate.setObjective(new Objective(updateLevel.objective().maxCO2(), updateLevel.objective().maxCoins()));
        levelTemplate.setSeason(updateLevel.season());
        levelTemplate.setCost(updateLevel.cost());

        levelTemplate.getTransformers().clear();
        levelTemplate.getTransformers().addAll(
                updateLevel.transformers().stream().map(updateTransformer -> new LevelTransformer(
                        updateTransformer.congestion(),
                        updateTransformer.houses().stream().map(updateHouse -> new LevelHouse(
                                new DayProfile(updateLevel.season()),
                                new HouseOptions(
                                        updateHouse.hasHeatPump(),
                                        updateHouse.hasElectricVehicle(),
                                        updateHouse.congestion(),
                                        updateHouse.maxSolarPanels(),
                                        updateHouse.maxBatteries()
                                )
                        )).toList(),
                        levelTemplate,
                        updateTransformer.maxBatteryCount()
                )).toList()
        );

        LevelTemplate savedLevelTemplate = this.levelTemplateRepository.save(levelTemplate);

        return LevelTemplateDTO.fromEntity(savedLevelTemplate);
    }

    public List<LevelTemplateDTO> getAllLevels() {
        return this.levelTemplateRepository.findAll().stream().map(LevelTemplateDTO::fromEntity).toList();
    }

    public void deleteLevel(long id) {
        this.levelTemplateRepository.deleteById(id);
    }
}
