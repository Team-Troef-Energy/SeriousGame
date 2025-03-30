package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.GameLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameLevelRepository extends JpaRepository<GameLevel, Long> {
    GameLevel getGameLevelById(Long id);
}
