package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.usecase.ClientCrudUseCase;
import com.seek.clientmanager.domain.usecase.ClientInfoUseCase;
import com.seek.clientmanager.domain.usecase.ClientsStatsUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.EditClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientWithStatsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
@RequestMapping("/client")
@RestController
public class ClientController {

    private final ClientInfoUseCase clientInfoUseCase;
    private final ClientsStatsUseCase clientsStatsUseCase;
    private final ClientCrudUseCase clientCrudUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientDTO> createClient(@RequestBody NewClientDTO clientDTO) {
        return clientCrudUseCase.createClient(NewClientDTO.toClient(clientDTO))
                .map(ClientDTO::fromClient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> deleteClient(@PathVariable int id) {
        return clientCrudUseCase.deleteClient(id)
                .then(Mono.just("Client deleted successfully"));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDTO> updateClient(@RequestBody EditClientDTO clientDTO) {
        return clientCrudUseCase.updateClient(EditClientDTO.toClient(clientDTO))
                .map(ClientDTO::fromClient);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClientDTO> getClientById(@PathVariable int id) {
        return clientCrudUseCase.findById(id)
                .map(ClientDTO::fromClient);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClientWithStatsDTO> getAllClients() {
        return clientInfoUseCase.findAllClientDetails()
                .map(ClientWithStatsDTO::fromClientDetail);
    }

    @GetMapping("/age/std")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BigDecimal> getAgeStd() {
        return clientsStatsUseCase.calculateStdAge();
    }

    @GetMapping("/age/average")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BigDecimal> getAgeAverage() {
        return clientsStatsUseCase.calculateAverageAge();
    }

    @GetMapping("/birthdate/max")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> getMaxBirthDate() {
        return clientsStatsUseCase.calculateMaxBirthDate()
                .map(Date::toString);
    }

    @GetMapping("/birthdate/min")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> getMinBirthDate() {
        return clientsStatsUseCase.calculateMinBirthDate()
                .map(Date::toString);
    }

    @GetMapping("/birthdate/mode")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> getModeBirthDate() {
        return clientsStatsUseCase.calculateModeBirthDate()
                .map(Date::toString);
    }

}
