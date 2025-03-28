package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.usecase.UserCrudUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.UpdateUserRoleDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.UserDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.interfaces.AdminAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class AdminController implements AdminAPI {

    private final UserCrudUseCase userCrudUseCase;

    @Override
    public Flux<UserDTO> getAllUsers() {
        return userCrudUseCase.getAllUsers()
                .map(UserDTO::fromUser);
    }

    @Override
    public Mono<String> deleteUser(@PathVariable int id) {
        return userCrudUseCase.deleteUser(id)
                .then(Mono.just("User deleted successfully"));
    }

    @Override
    public Mono<UserDTO> updateUser(@RequestBody UpdateUserRoleDTO updateUserRoleDTO) {
        return userCrudUseCase.updateUserRole(updateUserRoleDTO.id(), updateUserRoleDTO.role())
                .map(UserDTO::fromUser);
    }
}
