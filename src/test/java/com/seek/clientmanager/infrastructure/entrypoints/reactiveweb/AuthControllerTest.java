package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.usecase.UserAuthUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewUserDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.LoginUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserAuthUseCase userAuthUseCase;

    @InjectMocks
    private AuthController authController;

    private NewUserDTO newUserDTO;
    private LoginUserDTO loginUserDTO;
    private Token dummyToken;

    @BeforeEach
    void setUp() {
        newUserDTO = new NewUserDTO("user@example.com", "plainPassword");
        loginUserDTO = new LoginUserDTO("user@example.com", "plainPassword");
        dummyToken = new Token("dummy-token");
    }

    @Test
    void register_success() {
        when(userAuthUseCase.register(any()))
                .thenReturn(Mono.just(dummyToken));

        Mono<Token> result = authController.register(newUserDTO);

        StepVerifier.create(result)
                .expectNext(dummyToken)
                .verifyComplete();
    }

    @Test
    void login_success() {
        when(userAuthUseCase.login(any()))
                .thenReturn(Mono.just(dummyToken));

        Mono<Token> result = authController.login(loginUserDTO);

        StepVerifier.create(result)
                .expectNext(dummyToken)
                .verifyComplete();
    }
}