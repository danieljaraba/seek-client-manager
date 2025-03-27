package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.handler;

import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDomainException(DomainException ex) {
        LOGGER.error("DomainException occurred: {}", ex.getMessage(), ex);
        LOGGER.debug(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorType().getHttpStatus().value(),
                ex.getErrorType().getHttpStatus().getReasonPhrase(),
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(ex.getErrorType().getHttpStatus().value()).body(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(Exception ex) {
        LOGGER.error("Exception occurred: {}", ex.getMessage());
        LOGGER.debug(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                ex.getMessage()
        );
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleAccessDeniedException(AccessDeniedException ex) {
        LOGGER.error("AccessDeniedException occurred: {}", ex.getMessage());
        LOGGER.debug(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "FORBIDDEN",
                "Access Denied"
        );
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse));
    }

}
