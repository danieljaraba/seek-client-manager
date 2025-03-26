package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.domain.model.gateways.EncoderGateway;
import com.seek.clientmanager.domain.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserCrudUseCase {

    private final UserRepository userRepository;
    private final EncoderGateway passwordEncoder;

    public Mono<User> createUser(User user) {
        return userRepository.existsByEmail(user.email())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DomainException(ErrorType.ALREADY_EXISTS, "User already exists"));
                    }
                    return encodePassword(user);
                })
                .flatMap(userRepository::save);
    }

    private Mono<User> encodePassword(User user) {
        return passwordEncoder.encode(user.password())
                .map(user::changePassword);
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")));
    }

    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.id())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")))
                .flatMap(existingUser -> encodePassword(user))
                .flatMap(userRepository::update);
    }

    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")))
                .flatMap(existingUser -> userRepository.deleteById(id));
    }

}
