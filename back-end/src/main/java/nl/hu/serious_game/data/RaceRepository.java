package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findByUserEmail(String userEmail);
}
