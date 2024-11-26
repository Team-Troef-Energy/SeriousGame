package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.LevelService;
import nl.hu.serious_game.application.dto.out.LevelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/levels")
public class LevelController {

    private final LevelService levelService;

    @Autowired
    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping("/start/{levelNumber}")
    public ResponseEntity<LevelDTO> startLevel(@PathVariable int levelNumber) {
        try {
            LevelDTO level = levelService.startLevel(levelNumber);
            System.out.println("levelNumber = " + levelNumber);
            return ResponseEntity.ok(level);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}