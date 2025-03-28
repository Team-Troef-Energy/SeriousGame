package nl.hu.serious_game.application;

import nl.hu.serious_game.application.dto.out.LevelTemplateDTO;
import nl.hu.serious_game.application.dto.in.LevelTemplateUpdateDTO;
import nl.hu.serious_game.data.LevelTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelTemplateService {
    private final LevelTemplateRepository levelTemplateRepository;

    @Autowired
    public LevelTemplateService(LevelTemplateRepository levelTemplateRepository) {
        this.levelTemplateRepository = levelTemplateRepository;
    }

    public LevelTemplateDTO createLevel(LevelTemplateService levelTemplateService) {
        // TODO
        return null;
    }

    public LevelTemplateDTO updateLevel(long id, LevelTemplateUpdateDTO levelTemplateUpdateDTO) {
        // TODO
        return null;
    }
}
