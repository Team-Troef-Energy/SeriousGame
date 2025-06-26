package nl.hu.serious_game.domain;

/// Not stored in the app database.
/// Retrieved from Firestore when necessary.
/// @see nl.hu.serious_game.application.FirestoreUserService
/// @see nl.hu.serious_game.application.UserService
public record User(
        String id,
        //String email,
        //String name,
        UserRole role
) {}
