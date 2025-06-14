package nl.hu.serious_game.application;

import nl.hu.serious_game.domain.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getUser(Authentication authentication);
}
