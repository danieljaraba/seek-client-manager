package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.usecase.UserAuthUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.LoginUserDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final UserAuthUseCase userAuthUseCase;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Token> register(@RequestBody NewUserDTO user) {
        return userAuthUseCase.register(user.toUser());
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Token> login(@RequestBody LoginUserDTO user) {
        return userAuthUseCase.login(user.toUser());
    }

}
