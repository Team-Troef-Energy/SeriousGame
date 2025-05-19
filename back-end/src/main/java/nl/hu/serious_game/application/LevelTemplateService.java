package nl.hu.serious_game.application;

import java.util.List;
import java.util.Optional;

import nl.hu.serious_game.domain.*;
import nl.hu.serious_game.data.RaceRepository;
import nl.hu.serious_game.data.LevelTransformerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import nl.hu.serious_game.application.dto.in.LevelTemplateCreateDTO;
import nl.hu.serious_game.application.dto.in.LevelTemplateUpdateDTO;
import nl.hu.serious_game.application.dto.out.LevelTemplateDTO;
import nl.hu.serious_game.data.LevelTemplateRepository;

@Service
public class LevelTemplateService {
    private final LevelTemplateRepository levelTemplateRepository;
    private final RaceRepository raceRepository;
    private final LevelTransformerRepository levelTransformerRepository;

    @Autowired
    public LevelTemplateService(LevelTemplateRepository levelTemplateRepository, LevelTransformerRepository levelTransformerRepository, RaceRepository raceRepository) {
        this.levelTemplateRepository = levelTemplateRepository;
        this.levelTransformerRepository = levelTransformerRepository;
        this.raceRepository = raceRepository;
    }

    public LevelTemplateDTO createLevel(LevelTemplateCreateDTO createLevel) {
        Race race;
        if (createLevel.levelType() == LevelType.GLOBAL) {
            if (createLevel.raceId() == null) {
                race = null;
            } else {
                throw new IllegalArgumentException("Cannot set raceId when LevelType is GLOBAL");
            }
        } else if (createLevel.levelType() == LevelType.RACE) {
            if (createLevel.raceId() == null) {
                throw new IllegalArgumentException("Must set raceId when LevelType is RACE");
            } else {
                Optional<Race> foundRace = this.raceRepository.findById(createLevel.raceId());
                if (foundRace.isPresent()) {
                    race = foundRace.get();
                } else {
                    throw new IllegalArgumentException("Race with ID %d is not found".formatted(createLevel.raceId()));
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid LevelType %s".formatted(createLevel.levelType().name()));
        }

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
                createLevel.cost(),
                createLevel.levelType(),
                race
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

        // https://stackoverflow.com/questions/24724152/jpa-clear-collection-and-add-new-items
        // Related entities must be deleted before being removed from the collection.
        // I'm not sure why this is.
        this.levelTransformerRepository.deleteAll(levelTemplate.getTransformers());
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
