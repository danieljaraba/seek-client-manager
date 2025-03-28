package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.model.User;
import com.seek.clientmanager.domain.usecase.UserCrudUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.UpdateUserRoleDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private UserCrudUseCase userCrudUseCase;

    @InjectMocks
    private AdminController adminController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1, "user1@example.com", "plainPassword", "ROLE_USER");
        user2 = new User(2, "user2@example.com", "plainPassword", "ROLE_USER");
    }

    @Test
    void getAllUsers() {
        when(userCrudUseCase.getAllUsers()).thenReturn(Flux.just(user1, user2));

        Flux<UserDTO> result = adminController.getAllUsers();

        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.id() == 1 && dto.email().equals(user1.email()))
                .expectNextMatches(dto -> dto.id() == 2 && dto.email().equals(user2.email()))
                .verifyComplete();
    }

    @Test
    void deleteUser() {
        int userId = 1;
        when(userCrudUseCase.deleteUser(eq(userId))).thenReturn(Mono.empty());

        Mono<String> result = adminController.deleteUser(userId);

        StepVerifier.create(result)
                .expectNext("User deleted successfully")
                .verifyComplete();
    }

    @Test
    void updateUser() {
        int userId = 1;
        String newRole = "ROLE_ADMIN";
        UpdateUserRoleDTO dto = new UpdateUserRoleDTO(userId, newRole);
        User updatedUser = new User(userId, "user1@example.com", "plainPassword", newRole);
        when(userCrudUseCase.updateUserRole(eq(userId), eq(newRole))).thenReturn(Mono.just(updatedUser));

        Mono<UserDTO> result = adminController.updateUser(dto);

        StepVerifier.create(result)
                .expectNextMatches(userDTO -> userDTO.id() == userId &&
                        userDTO.role().equals(newRole))
                .verifyComplete();
    }
}