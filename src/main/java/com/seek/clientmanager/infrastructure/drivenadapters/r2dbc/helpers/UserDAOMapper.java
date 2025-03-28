package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.helpers;

import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data.UserDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserDAOMapper {

    User toUser(UserDAO userDAO);

    UserDAO toUserDAO(User user);

    default Mono<User> toUser(Mono<UserDAO> userDAO) {
        return userDAO.map(this::toUser);
    }

    default Mono<UserDAO> toUserDAO(Mono<User> user) {
        return user.map(this::toUserDAO);
    }

    default Flux<User> toUser(Flux<UserDAO> userDAO) {
        return userDAO.map(this::toUser);
    }

    default Flux<UserDAO> toUserDAO(Flux<User> user) {
        return user.map(this::toUserDAO);
    }

}
