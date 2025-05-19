package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.LevelTemplate;
import nl.hu.serious_game.domain.LevelTransformer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelTransformerRepository extends JpaRepository<LevelTransformer, Long> {
}
