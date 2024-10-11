package nl.hu.serious_game.presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scores")
public class GameController {
    private final GameService gameService;
    public GameController(GameService gameService) {
        this.scoreService = scoreService;
    }
}
