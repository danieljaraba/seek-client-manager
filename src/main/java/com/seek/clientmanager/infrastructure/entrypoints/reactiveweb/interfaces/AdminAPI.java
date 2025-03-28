package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.interfaces;

import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.UpdateUserRoleDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestBody;

@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public interface AdminAPI {

    @Operation(
            summary = "Get All Users",
            description = "Retrieves a list of all users.",
            tags = {"admin"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved users",
                    content = @Content(
                            schema = @Schema(implementation = UserDTO.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    Flux<UserDTO> getAllUsers();

    @Operation(
            summary = "Delete User",
            description = "Deletes a user by id.",
            tags = {"admin"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<String> deleteUser(
            @PathVariable
            @Parameter(
                    description = "User ID",
                    required = true,
                    example = "1"
            )
            int id
    );

    @Operation(
            summary = "Update User Role",
            description = "Updates the role of a user.",
            tags = {"admin"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = UserDTO.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access Denied",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    @PatchMapping("/users/role")
    @ResponseStatus(HttpStatus.OK)
    Mono<UserDTO> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User role update data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateUserRoleDTO.class),
                            mediaType = "application/json"
                    )
            )
            @Valid
            @RequestBody
            UpdateUserRoleDTO updateUserRoleDTO
    );
}