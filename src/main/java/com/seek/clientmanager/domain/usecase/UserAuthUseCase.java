package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.domain.model.gateways.EncoderGateway;
import com.seek.clientmanager.domain.model.gateways.SecurityGateway;
import com.seek.clientmanager.domain.model.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserAuthUseCase {

    private final SecurityGateway securityGateway;
    private final EncoderGateway encoderGateway;
    private final UserRepository userRepository;
    private final UserCrudUseCase userCrudUseCase;

    public Mono<Token> login(User user) {
        return userRepository.findByEmail(user.email())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "User not found")))
                .filterWhen(existingUser -> encoderGateway.matches(user.password(), existingUser.password()))
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.UNAUTHORIZED, "Invalid password")))
                .flatMap(securityGateway::generateToken);
    }

    public Mono<Token> register(User user) {
        return userCrudUseCase.createUser(user)
                .flatMap(securityGateway::generateToken);
    }

}
