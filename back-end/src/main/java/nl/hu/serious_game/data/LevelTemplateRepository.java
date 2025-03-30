package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.LevelTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LevelTemplateRepository extends JpaRepository<LevelTemplate, Long> {
    @Query("select count(*) from GameLevel")
    int getLevelCount();

    LevelTemplate getLevelTemplateById(Long id);

    Optional<LevelTemplate> getLevelTemplateByLevelNumber(int templateNumber);
}
