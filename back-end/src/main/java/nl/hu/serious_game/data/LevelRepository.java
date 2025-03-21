package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LevelRepository extends JpaRepository<Level, Long> {
    @Query("select count(*) from Level")
    int getLevelCount();

    Level getLevelById(Long levelNumber);
}
