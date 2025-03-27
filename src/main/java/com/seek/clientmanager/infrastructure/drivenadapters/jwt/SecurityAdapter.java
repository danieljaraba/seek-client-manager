package com.seek.clientmanager.infrastructure.drivenadapters.jwt;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.model.gateways.SecurityGateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class SecurityAdapter implements SecurityGateway {

    @Value("${adapter.jwt.secret}")
    private String secret;

    @Value("${adapter.jwt.expiration}")
    private Long expiration;

    @Override
    public Mono<Token> generateToken(User user) {
        return Mono.just(
                Jwts.builder()
                        .setSubject(user.email())
                        .claim("role", user.role())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + expiration))
                        .signWith(getKey(secret))
            )
            .map(builder -> new Token(builder.compact()));
    }

    @Override
    public Mono<Boolean> validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Mono.just(claims.getExpiration().after(new Date()));
        } catch (Exception ex) {
            return Mono.just(false);
        }
    }

    @Override
    public Mono<String> getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Mono.just(claims.getSubject());
        } catch (Exception ex) {
            return Mono.empty();
        }
    }

    @Override
    public Mono<String> getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Mono.just(claims.get("role", String.class));
        } catch (Exception ex) {
            return Mono.empty();
        }
    }

    private Key getKey(String s) {
        return Keys.hmacShaKeyFor(s.getBytes());
    }

}
