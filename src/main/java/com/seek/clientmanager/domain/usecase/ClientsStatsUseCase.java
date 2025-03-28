package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.gateways.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class ClientsStatsUseCase {

    private final ClientRepository clientRepository;

    public Mono<BigDecimal> calculateAverageAge() {
        return clientRepository.findAverageAge();
    }

    public Mono<BigDecimal> calculateStdAge() {
        return clientRepository.findStdAge();
    }

    public Mono<Date> calculateMaxBirthDate() {
        return clientRepository.findMaxBirthDate();
    }

    public Mono<Date> calculateMinBirthDate() {
        return clientRepository.findMinBirthDate();
    }

    public Mono<Date> calculateModeBirthDate() {
        return clientRepository.findModeBirthDate();
    }

}
