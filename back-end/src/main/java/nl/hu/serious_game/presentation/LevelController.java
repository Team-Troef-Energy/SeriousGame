package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.LevelService;
import nl.hu.serious_game.application.dto.in.LevelUpdateDTO;
import nl.hu.serious_game.application.dto.out.LevelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            System.out.println("start: levelNumber = " + levelNumber);
            return ResponseEntity.ok(level);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{levelNumber}")
    public ResponseEntity<LevelDTO> updateLevel(@PathVariable int levelNumber, @RequestBody LevelUpdateDTO levelUpdateDTO) {
        try {
            LevelDTO updatedLevel = levelService.updateLevel(levelNumber, levelUpdateDTO);
            System.out.println("update: levelNumber = " + levelNumber);
            return ResponseEntity.ok(updatedLevel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalLevels() {
        return ResponseEntity.ok(levelService.getTotalLevels());
    }
}
