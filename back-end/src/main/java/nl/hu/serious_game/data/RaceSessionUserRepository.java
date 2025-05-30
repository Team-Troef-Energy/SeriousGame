package nl.hu.serious_game.data;

import nl.hu.serious_game.domain.RaceSessionUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RaceSessionUserRepository extends JpaRepository<RaceSessionUser, Long> {
    Optional<RaceSessionUser> findByToken(String token);
}
