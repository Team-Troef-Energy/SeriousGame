package nl.hu.serious_game.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String key;

    UserRole(String key) {
        this.key = key;
    }

    public static boolean allowAccess(UserRole requiredRole, UserRole givenRole) {
        return switch (requiredRole) {
            case USER -> true;
            case ADMIN -> givenRole == ADMIN;
        };
    }

    public static UserRole fromKey(String key) {
        return switch (key) {
            case "user" -> USER;
            case "admin" -> ADMIN;
            default -> throw new IllegalArgumentException("UserRole key %s is not recognized".formatted(key));
        };
    }
}
