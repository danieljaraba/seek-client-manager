package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.usecase.UserAuthUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.LoginUserDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final UserAuthUseCase userAuthUseCase;

    @PostMapping("/register")
    public Mono<Token> register(@RequestBody NewUserDTO user) {
        return userAuthUseCase.register(user.toUser());
    }

    @PostMapping("/login")
    public Mono<Token> login(@RequestBody LoginUserDTO user) {
        return userAuthUseCase.login(user.toUser());
    }

}
