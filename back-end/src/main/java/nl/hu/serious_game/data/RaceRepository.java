package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {
}
