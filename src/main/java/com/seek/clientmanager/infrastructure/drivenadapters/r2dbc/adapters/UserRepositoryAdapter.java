package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.adapters;

import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.model.gateways.UserRepository;
import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.helpers.UserDAOMapper;
import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.repositories.UserDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@AllArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserDAORepository userDAORepository;
    private final UserDAOMapper mapper;

    @Override
    public Mono<User> findByEmail(String email) {
        return userDAORepository.findByEmail(email)
                .map(mapper::toUser);
    }

    @Override
    public Mono<User> save(User user) {
        return Mono.just(user)
                .map(mapper::toUserDAO)
                .flatMap(userDAORepository::save)
                .map(mapper::toUser);
    }

    @Override
    public Mono<User> findById(String id) {
        return userDAORepository.findById(UUID.fromString(id))
                .map(mapper::toUser);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return userDAORepository.deleteById(UUID.fromString(id));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userDAORepository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return userDAORepository.existsById(UUID.fromString(id));
    }

    @Override
    public Mono<User> update(User user) {
        return Mono.just(user)
                .map(mapper::toUserDAO)
                .flatMap(userDAORepository::save)
                .map(mapper::toUser);
    }

    @Override
    public Mono<String> findPasswordByEmail(String email) {
        return userDAORepository.findPasswordByEmail(email);
    }
}
