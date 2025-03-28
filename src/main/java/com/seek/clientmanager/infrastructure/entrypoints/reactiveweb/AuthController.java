package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.usecase.UserAuthUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.LoginUserDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewUserDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.interfaces.AuthAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class AuthController implements AuthAPI {

    private final UserAuthUseCase userAuthUseCase;

    @Override
    public Mono<Token> register(@RequestBody NewUserDTO user) {
        return userAuthUseCase.register(user.toUser());
    }

    @Override
    public Mono<Token> login(@RequestBody LoginUserDTO user) {
        return userAuthUseCase.login(user.toUser());
    }

}
