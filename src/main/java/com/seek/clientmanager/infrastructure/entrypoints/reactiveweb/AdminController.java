package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.usecase.UserCrudUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.UpdateUserRoleDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final UserCrudUseCase userCrudUseCase;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    Flux<UserDTO> getAllUsers() {
        return userCrudUseCase.getAllUsers()
                .map(UserDTO::fromUser);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<String> deleteUser(@PathVariable int id) {
        return userCrudUseCase.deleteUser(id)
                .then(Mono.just("User deleted successfully"));
    }

    @PatchMapping("/users/role")
    @ResponseStatus(HttpStatus.OK)
    Mono<UserDTO> updateUser(@RequestBody UpdateUserRoleDTO updateUserRoleDTO) {
        return userCrudUseCase.updateUserRole(updateUserRoleDTO.id(), updateUserRoleDTO.role())
                .map(UserDTO::fromUser);
    }
}
