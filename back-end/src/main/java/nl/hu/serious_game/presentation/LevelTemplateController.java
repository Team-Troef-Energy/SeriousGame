package nl.hu.serious_game.presentation;

import java.util.List;

import nl.hu.serious_game.application.aspect.RequireRole;
import nl.hu.serious_game.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nl.hu.serious_game.application.LevelTemplateService;
import nl.hu.serious_game.application.dto.in.LevelTemplateCreateDTO;
import nl.hu.serious_game.application.dto.in.LevelTemplateUpdateDTO;
import nl.hu.serious_game.application.dto.out.LevelTemplateDTO;

@RestController
@RequestMapping("/templates")
public class LevelTemplateController {
    private final LevelTemplateService levelTemplateService;

    @Autowired
    public LevelTemplateController(LevelTemplateService levelTemplateService) {
        this.levelTemplateService = levelTemplateService;
    }

    @GetMapping
    @RequireRole(role = UserRole.ANONYMOUS)
    public ResponseEntity<List<LevelTemplateDTO>> getAllLevels() {
        return ResponseEntity.ok(
                levelTemplateService.getAllGlobalLevels()
                        .stream()
                        .map(LevelTemplateDTO::fromEntity)
                        .toList()
        );
    }

    @PostMapping("")
    @RequireRole(role = UserRole.USER)
    public ResponseEntity<?> createLevelTemplate(@Validated @RequestBody LevelTemplateCreateDTO levelTemplateCreateDTO) {
        try {
            LevelTemplateDTO levelTemplate = levelTemplateService.createLevel(levelTemplateCreateDTO);
            return ResponseEntity.ok(levelTemplate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}")
    @RequireRole(role = UserRole.USER)
    public ResponseEntity<?> updateLevelTemplate(@PathVariable long id, @Validated @RequestBody LevelTemplateUpdateDTO levelTemplateUpdateDTO) {
        try {
            LevelTemplateDTO updatedTemplate = levelTemplateService.updateLevel(id, levelTemplateUpdateDTO);
            return ResponseEntity.ok(updatedTemplate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequireRole(role = UserRole.USER)
    public void deleteLevel(@PathVariable long id) {
        levelTemplateService.deleteLevel(id);
    }
}
