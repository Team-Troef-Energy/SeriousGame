package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.LevelTemplate;
import nl.hu.serious_game.domain.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LevelTemplateRepository extends JpaRepository<LevelTemplate, Long> {
    @Query("select count(*) from LevelTemplate")
    int getLevelCount();

    Optional<LevelTemplate> getLevelTemplateById(Long id);

    Optional<LevelTemplate> getLevelTemplateByLevelNumber(int templateNumber);

    List<LevelTemplate> findAllByType(LevelType type);
}
