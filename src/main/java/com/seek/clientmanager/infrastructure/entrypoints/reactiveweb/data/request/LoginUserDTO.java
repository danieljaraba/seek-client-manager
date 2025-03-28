package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request;

import com.seek.clientmanager.domain.model.User;

public record LoginUserDTO(
        String email,
        String password
) {

    public User toUser() {
        return new User(
                0,
                email(),
                password(),
                null
        );
    }

}
