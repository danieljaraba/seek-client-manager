package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request;

public record UpdateUserRoleDTO(
        int id,
        String role
) {
}
