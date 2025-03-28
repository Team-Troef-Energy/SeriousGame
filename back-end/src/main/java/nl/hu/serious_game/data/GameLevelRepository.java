package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.GameLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameLevelRepository extends JpaRepository<GameLevel, Long> {
    @Query("select count(*) from GameLevel")
    int getLevelCount();

    GameLevel getGameLevelById(Long id);
}

