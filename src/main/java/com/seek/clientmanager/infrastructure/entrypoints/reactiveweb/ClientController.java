package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.usecase.ClientInfoUseCase;
import com.seek.clientmanager.domain.usecase.ClientsStatsUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientWithStatsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
