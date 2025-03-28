package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.LevelTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LevelTemplateRepository extends JpaRepository<LevelTemplate, Long> {
    @Query("select count(*) from GameLevel")
    int getLevelCount();

    LevelTemplate getLevelTemplateById(Long id);
}
