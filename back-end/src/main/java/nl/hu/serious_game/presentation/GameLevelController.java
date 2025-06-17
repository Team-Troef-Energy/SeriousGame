package nl.hu.serious_game.presentation;

import nl.hu.serious_game.application.GameLevelService;
import nl.hu.serious_game.application.aspect.RequireRole;
import nl.hu.serious_game.application.dto.in.GameLevelUpdateDTO;
import nl.hu.serious_game.application.dto.out.GameLevelDTO;
import nl.hu.serious_game.domain.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/levels")
public class GameLevelController {
    private final Logger logger = LoggerFactory.getLogger(GameLevelController.class);
    private final GameLevelService gameLevelService;

    @Autowired
    public GameLevelController(GameLevelService gameLevelService) {
        this.gameLevelService = gameLevelService;
    }

    @GetMapping("/start/{levelTemplateId}")
    @RequireRole(role = UserRole.ANONYMOUS)
    public ResponseEntity<GameLevelDTO> startLevel(@PathVariable long levelTemplateId) {
        try {
            GameLevelDTO level = gameLevelService.startGame(levelTemplateId);
            logger.debug("start: levelTemplateId = {}", levelTemplateId);
            return ResponseEntity.ok(level);
        } catch (IllegalArgumentException e) {
            logger.error("Error starting level", e);

            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update/{gameLevelId}")
    @RequireRole(role = UserRole.ANONYMOUS)
    public ResponseEntity<GameLevelDTO> updateLevel(@PathVariable long gameLevelId, @RequestBody GameLevelUpdateDTO levelUpdateDTO) {
        try {
            GameLevelDTO updatedLevel = gameLevelService.updateGame(gameLevelId, levelUpdateDTO);
            logger.debug("update: gameLevelId = {}", gameLevelId);
            return ResponseEntity.ok(updatedLevel);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating level", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
