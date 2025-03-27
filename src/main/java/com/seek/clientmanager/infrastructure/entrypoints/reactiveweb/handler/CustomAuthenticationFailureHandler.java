package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange exchange, AuthenticationException exception) {
        LOGGER.error("Authentication error: {}", exception.getMessage());

        if (exception.getMessage().equalsIgnoreCase("Invalid token")) {
            exchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getExchange().getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            String jsonError = "{" +
                    "\"status\":" + HttpStatus.UNAUTHORIZED.value() + "," +
                    "\"errorCode\":\"BAD_CREDENTIALS\"," +
                    "\"message\":\"Invalid token or credentials\"" +
                    "}";

            byte[] jsonBytes = jsonError.getBytes();
            return exchange.getExchange().getResponse().writeWith(Mono.just(exchange.getExchange().getResponse().bufferFactory().wrap(jsonBytes)));
        }
        exchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getExchange().getResponse().setComplete();
    }
}
