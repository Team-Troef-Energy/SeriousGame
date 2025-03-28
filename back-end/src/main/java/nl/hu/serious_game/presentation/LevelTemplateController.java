package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.LevelTemplateService;
import nl.hu.serious_game.application.dto.in.LevelTemplateCreateDTO;
import nl.hu.serious_game.application.dto.out.LevelTemplateDTO;
import nl.hu.serious_game.application.dto.in.LevelTemplateUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/templates")
public class LevelTemplateController {
    private final LevelTemplateService levelTemplateService;

    @Autowired
    public LevelTemplateController(LevelTemplateService levelTemplateService) {
        this.levelTemplateService = levelTemplateService;
    }

    @PostMapping("/create")
    public ResponseEntity<LevelTemplateDTO> createLevelTemplate(@RequestBody LevelTemplateCreateDTO levelTemplateCreateDTO) {
        try {
            LevelTemplateDTO levelTemplate = levelTemplateService.createLevel(levelTemplateService);
            return ResponseEntity.ok(levelTemplate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<LevelTemplateDTO> updateLevelTemplate(@PathVariable long id, @RequestBody LevelTemplateUpdateDTO levelTemplateUpdateDTO) {
        try {
            LevelTemplateDTO updatedTemplate = levelTemplateService.updateLevel(id, levelTemplateUpdateDTO);
            return ResponseEntity.ok(updatedTemplate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
