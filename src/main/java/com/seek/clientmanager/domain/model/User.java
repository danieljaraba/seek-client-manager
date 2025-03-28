package com.seek.clientmanager.domain.model;

public record User(
        int id,
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

    public User changeRole(String newRole) {
        return new User(
                id(),
                email(),
                password(),
                newRole
        );
    }

}
