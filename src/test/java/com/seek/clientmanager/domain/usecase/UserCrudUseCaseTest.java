package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.domain.model.gateways.EncoderGateway;
import com.seek.clientmanager.domain.model.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCrudUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EncoderGateway passwordEncoder;

    @InjectMocks
    private UserCrudUseCase userCrudUseCase;

    private User testUser;
    private User encodedUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1, "user@example.com", "plainPassword", "ROLE_USER");
        encodedUser = testUser.changePassword("encodedPassword");
    }

    @Test
    void createUser_success() {
        when(userRepository.existsByEmail(eq(testUser.email())))
                .thenReturn(Mono.just(false));
        when(userRepository.save(any(User.class)))
                .thenReturn(Mono.just(testUser));

        Mono<User> result = userCrudUseCase.createUser(testUser);

        StepVerifier.create(result)
                .expectNext(testUser)
                .verifyComplete();
    }

    @Test
    void createUser_alreadyExists() {
        when(userRepository.existsByEmail(eq(testUser.email())))
                .thenReturn(Mono.just(true));

        Mono<User> result = userCrudUseCase.createUser(testUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.ALREADY_EXISTS &&
                                throwable.getMessage().contains("User already exists")
                )
                .verify();
    }

    @Test
    void findById_found() {
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.just(testUser));

        Mono<User> result = userCrudUseCase.findById(testUser.id());

        StepVerifier.create(result)
                .expectNext(testUser)
                .verifyComplete();
    }

    @Test
    void findById_notFound() {
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.empty());

        Mono<User> result = userCrudUseCase.findById(testUser.id());

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("User not found")
                )
                .verify();
    }

    @Test
    void updateUser_success() {
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.just(testUser));
        when(passwordEncoder.encode(eq(testUser.password())))
                .thenReturn(Mono.just("encodedPassword"));
        when(userRepository.update(any(User.class)))
                .thenReturn(Mono.just(encodedUser));

        Mono<User> result = userCrudUseCase.updateUser(testUser);

        StepVerifier.create(result)
                .expectNext(encodedUser)
                .verifyComplete();
    }

    @Test
    void updateUser_notFound() {
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.empty());

        Mono<User> result = userCrudUseCase.updateUser(testUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("User not found")
                )
                .verify();
    }

    @Test
    void updateUserRole_success() {
        String newRole = "ROLE_ADMIN";
        User userWithNewRole = testUser.changeRole(newRole);
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.just(testUser), Mono.just(userWithNewRole));
        when(userRepository.updateUserRole(eq(testUser.id()), eq(newRole)))
                .thenReturn(Mono.empty());

        Mono<User> result = userCrudUseCase.updateUserRole(testUser.id(), newRole);

        StepVerifier.create(result)
                .expectNext(userWithNewRole)
                .verifyComplete();
    }

    @Test
    void updateUserRole_notFound() {
        String newRole = "ROLE_ADMIN";
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.empty());

        Mono<User> result = userCrudUseCase.updateUserRole(testUser.id(), newRole);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("User not found")
                )
                .verify();
    }

    @Test
    void deleteUser_success() {
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.just(testUser));
        when(userRepository.deleteById(eq(testUser.id())))
                .thenReturn(Mono.empty());

        Mono<Void> result = userCrudUseCase.deleteUser(testUser.id());

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteUser_notFound() {
        when(userRepository.findById(eq(testUser.id())))
                .thenReturn(Mono.empty());

        Mono<Void> result = userCrudUseCase.deleteUser(testUser.id());

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("User not found")
                )
                .verify();
    }

    @Test
    void getAllUsers_success() {
        User user2 = new User(2, "another@example.com", "password", "ROLE_USER");
        when(userRepository.findAll())
                .thenReturn(Flux.just(testUser, user2));

        Flux<User> result = userCrudUseCase.getAllUsers();

        StepVerifier.create(result)
                .expectNext(testUser)
                .expectNext(user2)
                .verifyComplete();
    }
}