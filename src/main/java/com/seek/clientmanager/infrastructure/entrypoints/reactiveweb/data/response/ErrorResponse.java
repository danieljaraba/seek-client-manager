package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response;

public record ErrorResponse(
        int status,
        String errorCode,
        String message
) {
}
