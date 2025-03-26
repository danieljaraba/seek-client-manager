package com.seek.clientmanager.domain.model;

import lombok.Builder;

@Builder
public record Token(
        String token
) {
}
