package com.seek.clientmanager.domain.model.gateways;

import com.seek.clientmanager.domain.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> findByEmail(String email);

    Mono<User> save(User user);

    Mono<User> findById(String id);

    Mono<Void> deleteById(String id);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsById(String id);

    Mono<User> update(User user);

    Mono<String> findPasswordByEmail(String email);

    Flux<User> findAll();

    Mono<Void> updateUserRole(String id, String role);

}
