package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.adapters;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.domain.model.gateways.ClientRepository;
import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.helpers.ClientDAOMapper;
import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.repositories.ClientDAORepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Repository
public class ClientRepositoryAdapter implements ClientRepository {

    private final ClientDAORepository clientDAORepository;
    private final ClientDAOMapper mapper;

    @Override
    public Flux<Client> findAll() {
        return clientDAORepository.findAll()
                .map(mapper::toClient);
    }

    @Override
    public Flux<Integer> findAllAges() {
        return clientDAORepository.findAllAges();
    }

    @Override
    public Flux<Date> findAllBirthDates() {
        return clientDAORepository.findAllBirthdates()
                .map(mapper::toDate);
    }

    @Override
    public Flux<String> findAllNames() {
        return clientDAORepository.findAllNames();
    }

    @Override
    public Flux<String> findAllLastNames() {
        return clientDAORepository.findAllLastNames();
    }

    @Override
    public Mono<BigDecimal> findAverageAge() {
        return clientDAORepository.findAverageAge()
                .map(mapper::toBigDecimal);
    }

    @Override
    public Mono<BigDecimal> findStdAge() {
        return clientDAORepository.findStdAge()
                .map(mapper::toBigDecimal);
    }

    @Override
    public Mono<Date> findMaxBirthDate() {
        return clientDAORepository.findMaxBirthDate()
                .map(mapper::toDate);
    }

    @Override
    public Mono<Date> findMinBirthDate() {
        return clientDAORepository.findMinBirthDate()
                .map(mapper::toDate);
    }

    @Override
    public Mono<Date> findModeBirthDate() {
        return clientDAORepository.findModeBirthDate()
                .map(mapper::toDate);
    }
}
