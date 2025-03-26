package com.seek.clientmanager.domain.model;

public record User(
        String id,
        String email,
        String password,
        String role
) {

    public User changePassword(String newPassword) {
        return new User(
                id(),
                email(),
                newPassword,
                role()
        );
    }

}
