package com.seek.clientmanager.domain.model.gateways;

import reactor.core.publisher.Mono;

public interface EncoderGateway {

    Mono<String> encode(String value);

    Mono<Boolean> matches(String value, String encodedValue);

}
