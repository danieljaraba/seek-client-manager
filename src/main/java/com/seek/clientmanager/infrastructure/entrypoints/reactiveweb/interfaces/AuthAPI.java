package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.interfaces;

import com.seek.clientmanager.domain.model.Token;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.LoginUserDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@RequestMapping("/auth")
public interface AuthAPI {

    @Operation(
            summary = "Register",
            description = "Register",
            tags = {"auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Register",
                            content = @Content(
                                    schema = @Schema(implementation = Token.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User Already Exists",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    ),
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Token> register(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Register",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = NewUserDTO.class)
                    )
            )
            NewUserDTO user
    );

    @Operation(
            summary = "Login",
            description = "Login",
            tags = {"auth"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login",
                            content = @Content(
                                    schema = @Schema(implementation = Token.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    mediaType = "application/json"
                            )
                    ),
            }
    )
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    Mono<Token> login(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginUserDTO.class)
                    )
            )
            LoginUserDTO user
    );

}
