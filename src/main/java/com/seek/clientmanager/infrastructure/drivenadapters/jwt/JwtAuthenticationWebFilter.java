package com.seek.clientmanager.infrastructure.drivenadapters.jwt;

import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.handler.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    @Autowired
    public JwtAuthenticationWebFilter(JwtAuthenticationManager authenticationManager,
                                      CustomAuthenticationFailureHandler authenticationFailureHandler) {
        super(authenticationManager);
        setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.anyExchange());
        setServerAuthenticationConverter(this::convert);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    private Mono<Authentication> convert(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
        }
        return Mono.empty();
    }
}
