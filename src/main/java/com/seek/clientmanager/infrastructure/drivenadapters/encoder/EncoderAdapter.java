package com.seek.clientmanager.infrastructure.drivenadapters.encoder;

import com.seek.clientmanager.domain.model.gateways.EncoderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EncoderAdapter implements EncoderGateway {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<String> encode(String value) {
        return Mono.just(passwordEncoder.encode(value));
    }

    @Override
    public Mono<Boolean> matches(String value, String encodedValue) {
        return Mono.just(passwordEncoder.matches(value, encodedValue));
    }
}
