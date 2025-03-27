package com.seek.clientmanager.domain.model.gateways;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.model.User;
import reactor.core.publisher.Mono;

public interface SecurityGateway {

    Mono<Token> generateToken(User user);

    Mono<Boolean> validateToken(String token);

    Mono<String> getUsernameFromToken(String token);

    Mono<String> getRoleFromToken(String token);

}
