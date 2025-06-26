package nl.hu.serious_game.application;

import nl.hu.serious_game.domain.User;
import org.springframework.security.core.Authentication;

/// Provides a User entity given Authentication credentials.
/// @see FirestoreUserService
public interface UserService {
    User getUser(Authentication authentication);
}
