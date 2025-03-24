package com.seek.clientmanager.domain.model;

public record User(
        String id,
        String email,
        String password,
        String role
) {
}
