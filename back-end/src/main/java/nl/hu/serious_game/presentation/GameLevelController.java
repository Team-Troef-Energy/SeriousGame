package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.GameLevelService;
import nl.hu.serious_game.application.dto.in.GameLevelUpdateDTO;
import nl.hu.serious_game.application.dto.out.GameLevelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/levels")
public class GameLevelController {

    private final GameLevelService gameLevelService;

    @Autowired
    public GameLevelController(GameLevelService gameLevelService) {
        this.gameLevelService = gameLevelService;
    }

    @GetMapping("/start/{levelNumber}")
    public ResponseEntity<GameLevelDTO> startLevel(@PathVariable int levelNumber) {
        try {
            GameLevelDTO level = gameLevelService.startLevel(levelNumber);
            System.out.println("start: levelNumber = " + levelNumber);
            return ResponseEntity.ok(level);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{levelNumber}")
    public ResponseEntity<GameLevelDTO> updateLevel(@PathVariable int levelNumber, @RequestBody GameLevelUpdateDTO levelUpdateDTO) {
        try {
            GameLevelDTO updatedLevel = gameLevelService.updateLevel(levelNumber, levelUpdateDTO);
            System.out.println("update: levelNumber = " + levelNumber);
            return ResponseEntity.ok(updatedLevel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalLevels() {
        return ResponseEntity.ok(gameLevelService.getTotalLevels());
    }
}
