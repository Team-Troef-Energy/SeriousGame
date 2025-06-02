package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.RaceSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaceSessionRepository extends JpaRepository<RaceSession, Long> {
    Optional<RaceSession> findByJoinCode(String joinCode);
}
