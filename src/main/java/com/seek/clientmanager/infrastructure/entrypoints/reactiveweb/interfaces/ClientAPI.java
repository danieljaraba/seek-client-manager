package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.interfaces;

import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.EditClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientWithStatsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;

@RequestMapping("/client")
public interface ClientAPI {

    @Operation(
            summary = "Create Client",
            description = "Creates a new client.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Client created successfully",
                    content = @Content(
                            schema = @Schema(implementation = ClientDTO.class),
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
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ClientDTO> createClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New client data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = NewClientDTO.class),
                            mediaType = "application/json"
                    )
            )
            @Valid @RequestBody NewClientDTO clientDTO
    );

    @Operation(
            summary = "Delete Client",
            description = "Deletes a client by id.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Client deleted successfully",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<String> deleteClient(
            @Parameter(
                    description = "Client ID",
                    required = true,
                    example = "1"
            )
            @PathVariable int id
    );

    @Operation(
            summary = "Update Client",
            description = "Updates an existing client.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Client updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = ClientDTO.class),
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
            )
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    Mono<ClientDTO> updateClient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Client update data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EditClientDTO.class),
                            mediaType = "application/json"
                    )
            )
            @Valid @RequestBody EditClientDTO clientDTO
    );

    @Operation(
            summary = "Get Client By ID",
            description = "Retrieves a client by its ID.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Client retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = ClientDTO.class),
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<ClientDTO> getClientById(
            @Parameter(
                    description = "Client ID",
                    required = true,
                    example = "1"
            )
            @PathVariable int id
    );

    @Operation(
            summary = "Get All Clients",
            description = "Retrieves all clients with statistics.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Clients retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = ClientWithStatsDTO.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    Flux<ClientWithStatsDTO> getAllClients();

    @Operation(
            summary = "Get Age Standard Deviation",
            description = "Retrieves the standard deviation of clients' ages.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Standard deviation calculated",
                    content = @Content(
                            schema = @Schema(implementation = BigDecimal.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/age/std")
    @ResponseStatus(HttpStatus.OK)
    Mono<BigDecimal> getAgeStd();

    @Operation(
            summary = "Get Age Average",
            description = "Retrieves the average age of clients.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Average age calculated",
                    content = @Content(
                            schema = @Schema(implementation = BigDecimal.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/age/average")
    @ResponseStatus(HttpStatus.OK)
    Mono<BigDecimal> getAgeAverage();

    @Operation(
            summary = "Get Maximum Birth Date",
            description = "Retrieves the latest birth date among clients.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Maximum birth date retrieved",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/birthdate/max")
    @ResponseStatus(HttpStatus.OK)
    Mono<String> getMaxBirthDate();

    @Operation(
            summary = "Get Minimum Birth Date",
            description = "Retrieves the earliest birth date among clients.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Minimum birth date retrieved",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/birthdate/min")
    @ResponseStatus(HttpStatus.OK)
    Mono<String> getMinBirthDate();

    @Operation(
            summary = "Get Mode Birth Date",
            description = "Retrieves the most frequent birth date among clients.",
            tags = {"client"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Mode birth date retrieved",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json"
                    )
            )
    })
    @GetMapping("/birthdate/mode")
    @ResponseStatus(HttpStatus.OK)
    Mono<String> getModeBirthDate();
}