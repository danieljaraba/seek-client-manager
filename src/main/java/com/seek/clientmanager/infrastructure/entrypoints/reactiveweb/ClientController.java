package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.usecase.ClientCrudUseCase;
import com.seek.clientmanager.domain.usecase.ClientInfoUseCase;
import com.seek.clientmanager.domain.usecase.ClientsStatsUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.EditClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientWithStatsDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.interfaces.ClientAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
@RestController
public class ClientController implements ClientAPI {

    private final ClientInfoUseCase clientInfoUseCase;
    private final ClientsStatsUseCase clientsStatsUseCase;
    private final ClientCrudUseCase clientCrudUseCase;

    @Override
    public Mono<ClientDTO> createClient(@RequestBody NewClientDTO clientDTO) {
        return clientCrudUseCase.createClient(NewClientDTO.toClient(clientDTO))
                .map(ClientDTO::fromClient);
    }

    @Override
    public Mono<String> deleteClient(@PathVariable int id) {
        return clientCrudUseCase.deleteClient(id)
                .then(Mono.just("Client deleted successfully"));
    }

    @Override
    public Mono<ClientDTO> updateClient(@RequestBody EditClientDTO clientDTO) {
        return clientCrudUseCase.updateClient(EditClientDTO.toClient(clientDTO))
                .map(ClientDTO::fromClient);
    }

    @Override
    public Mono<ClientDTO> getClientById(@PathVariable int id) {
        return clientCrudUseCase.findById(id)
                .map(ClientDTO::fromClient);
    }

    @Override
    public Flux<ClientWithStatsDTO> getAllClients() {
        return clientInfoUseCase.findAllClientDetails()
                .map(ClientWithStatsDTO::fromClientDetail);
    }

    @Override
    public Mono<BigDecimal> getAgeStd() {
        return clientsStatsUseCase.calculateStdAge();
    }

    @Override
    public Mono<BigDecimal> getAgeAverage() {
        return clientsStatsUseCase.calculateAverageAge();
    }

    @Override
    public Mono<String> getMaxBirthDate() {
        return clientsStatsUseCase.calculateMaxBirthDate()
                .map(Date::toString);
    }

    @Override
    public Mono<String> getMinBirthDate() {
        return clientsStatsUseCase.calculateMinBirthDate()
                .map(Date::toString);
    }

    @Override
    public Mono<String> getModeBirthDate() {
        return clientsStatsUseCase.calculateModeBirthDate()
                .map(Date::toString);
    }

}
