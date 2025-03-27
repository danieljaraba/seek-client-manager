package com.seek.clientmanager.domain.model;

import java.util.UUID;

public record User(
        String id,
        String email,
        String password,
        String role
) {

    public User generateId() {
        return new User(
                UUID.randomUUID().toString(),
                email(),
                password(),
                role()
        );
    }

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
