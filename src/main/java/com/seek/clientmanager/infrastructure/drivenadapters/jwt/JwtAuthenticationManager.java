package com.seek.clientmanager.infrastructure.drivenadapters.jwt;

import com.seek.clientmanager.domain.model.gateways.SecurityGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final SecurityGateway securityGateway;

    @Autowired
    public JwtAuthenticationManager(SecurityGateway securityGateway) {
        this.securityGateway = securityGateway;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        return securityGateway.validateToken(token)
                .flatMap(valid -> {
                    if (!valid) {
                        return Mono.empty();
                    }
                    return securityGateway.getUsernameFromToken(token)
                            .zipWith(securityGateway.getRoleFromToken(token))
                            .map(tuple -> new UsernamePasswordAuthenticationToken(
                                    tuple.getT1(),
                                    token,
                                    Collections.singletonList(new SimpleGrantedAuthority(tuple.getT2()))
                            ));
                });
    }
}
