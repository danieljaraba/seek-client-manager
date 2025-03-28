package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.domain.model.gateways.EncoderGateway;
import com.seek.clientmanager.domain.model.gateways.SecurityGateway;
import com.seek.clientmanager.domain.model.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthUseCaseTest {

    @Mock
    private SecurityGateway securityGateway;

    @Mock
    private EncoderGateway encoderGateway;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCrudUseCase userCrudUseCase;

    @InjectMocks
    private UserAuthUseCase userAuthUseCase;

    private User inputUser;
    private User storedUser;
    private Token dummyToken;

    @BeforeEach
    void setUp() {
        inputUser = new User(1,"user@example.com", "plainPassword", "ROLE_USER");
        storedUser = new User(2,"user@example.com", "encodedPassword", "ROLE_USER");
        dummyToken = new Token("dummy-token");
    }

    @Test
    void login_success() {
        when(userRepository.findByEmail(inputUser.email()))
                .thenReturn(Mono.just(storedUser));
        when(encoderGateway.matches(inputUser.password(), storedUser.password()))
                .thenReturn(Mono.just(true));
        when(securityGateway.generateToken(storedUser))
                .thenReturn(Mono.just(dummyToken));

        Mono<Token> result = userAuthUseCase.login(inputUser);

        StepVerifier.create(result)
                .expectNext(dummyToken)
                .verifyComplete();
    }

    @Test
    void login_userNotFound() {
        when(userRepository.findByEmail(inputUser.email()))
                .thenReturn(Mono.empty());

        Mono<Token> result = userAuthUseCase.login(inputUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("User not found")
                )
                .verify();
    }

    @Test
    void login_invalidPassword() {
        when(userRepository.findByEmail(inputUser.email()))
                .thenReturn(Mono.just(storedUser));
        when(encoderGateway.matches(inputUser.password(), storedUser.password()))
                .thenReturn(Mono.just(false));

        Mono<Token> result = userAuthUseCase.login(inputUser);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.UNAUTHORIZED &&
                                throwable.getMessage().contains("Invalid password")
                )
                .verify();
    }

    @Test
    void register_success() {
        User changedUser = inputUser.changeRole("ROLE_USER");
        when(userCrudUseCase.createUser(changedUser))
                .thenReturn(Mono.just(changedUser));
        when(securityGateway.generateToken(changedUser))
                .thenReturn(Mono.just(dummyToken));

        Mono<Token> result = userAuthUseCase.register(inputUser);

        StepVerifier.create(result)
                .expectNext(dummyToken)
                .verifyComplete();
    }
}