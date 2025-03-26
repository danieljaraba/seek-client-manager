package com.seek.clientmanager.domain.model.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorType {

    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found: %s"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request: %s"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized: %s"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: %s"),
    ALREADY_EXISTS(HttpStatus.CONFLICT, "Resource already exists: %s");

    private final HttpStatus httpStatus;
    private final String description;

    ErrorType(HttpStatus httpStatus, String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }

}

