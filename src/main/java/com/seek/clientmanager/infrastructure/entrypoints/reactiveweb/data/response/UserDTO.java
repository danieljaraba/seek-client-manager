package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response;

import com.seek.clientmanager.domain.model.User;

public record UserDTO(
        int id,
        String email,
        String role
) {

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.id(), user.email(), user.role());
    }

}
