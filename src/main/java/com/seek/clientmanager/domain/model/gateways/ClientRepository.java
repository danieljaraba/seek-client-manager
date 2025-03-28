package com.seek.clientmanager.domain.model.gateways;

import com.seek.clientmanager.domain.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

public interface ClientRepository {

    Flux<Client> findAll();

    Mono<Client> findById(int id);

    Mono<Client> save(Client client);

    Mono<Void> deleteById(int id);

    Mono<Client> update(Client client);

    Flux<Integer> findAllAges();

    Flux<Date> findAllBirthDates();

    Flux<String> findAllNames();

    Flux<String> findAllLastNames();

    Mono<BigDecimal> findAverageAge();

    Mono<BigDecimal> findStdAge();

    Mono<Date> findMaxBirthDate();

    Mono<Date> findMinBirthDate();

    Mono<Date> findModeBirthDate();

}
