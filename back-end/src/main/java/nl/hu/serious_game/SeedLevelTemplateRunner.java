package nl.hu.serious_game;

import nl.hu.serious_game.application.SeedLevelSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import nl.hu.serious_game.data.LevelTemplateRepository;

@Component
public class SeedLevelTemplateRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(SeedLevelTemplateRunner.class);
    private final LevelTemplateRepository levelTemplateRepository;
    private final SeedLevelSource seedLevelSource;

    @Autowired
    public SeedLevelTemplateRunner(LevelTemplateRepository levelTemplateRepository, SeedLevelSource seedLevelSource) {
        this.levelTemplateRepository = levelTemplateRepository;
        this.seedLevelSource = seedLevelSource;
    }

    @Override
    public void run(String... args) {
        logger.info("Runner started!");
        if (levelTemplateRepository.getLevelCount() == 0) {
            levelTemplateRepository.save(seedLevelSource.createLevel1());
            levelTemplateRepository.save(seedLevelSource.createLevel2());
            levelTemplateRepository.save(seedLevelSource.createLevel3());
            levelTemplateRepository.save(seedLevelSource.createLevel4());
        }
    }
}

