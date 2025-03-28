package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.domain.model.gateways.EncoderGateway;
import com.seek.clientmanager.domain.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserCrudUseCase {

    private final UserRepository userRepository;
    private final EncoderGateway passwordEncoder;
    private final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public Mono<User> createUser(User user) {
        return Mono.just(user)
                .flatMap(this::validateClient)
                .flatMap(existingUser -> userRepository.existsByEmail(existingUser.email()))
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DomainException(ErrorType.ALREADY_EXISTS, "User already exists"));
                    }
                    return Mono.just(user);
                })
                .flatMap(userRepository::save);
    }

    private Mono<User> encodePassword(User user) {
        return passwordEncoder.encode(user.password())
                .map(user::changePassword);
    }

    public Mono<User> findById(int id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")));
    }

    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.id())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")))
                .flatMap(existingUser -> encodePassword(user))
                .flatMap(userRepository::update);
    }

    public Mono<User> updateUserRole(int id, String role) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")))
                .flatMap(existingUser -> userRepository.updateUserRole(id, role))
                .then(userRepository.findById(id));
    }

    public Mono<Void> deleteUser(int id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")))
                .flatMap(existingUser -> userRepository.deleteById(id));
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    private  Mono<User> validateClient(User user) {
        return Mono.just(user)
                .flatMap(existingUser -> {
                    if (existingUser.email() == null || !existingUser.email().matches(emailRegex)) {
                        return Mono.error(new DomainException(ErrorType.INVALID_DATA, "Invalid email format"));
                    }
                    if (existingUser.password() == null || existingUser.password().length() < 8) {
                        return Mono.error(new DomainException(ErrorType.INVALID_DATA, "Password must be at least 8 characters long"));
                    }
                    return Mono.just(existingUser);
                });
    }

}
