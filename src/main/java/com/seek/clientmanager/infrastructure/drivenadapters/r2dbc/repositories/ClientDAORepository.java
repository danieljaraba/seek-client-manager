package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.repositories;

import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data.ClientDAO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface ClientDAORepository extends ReactiveCrudRepository<ClientDAO, Integer> {

    @Query("SELECT age FROM clients")
    Flux<Integer> findAllAges();

    @Query("SELECT birth_date FROM clients")
    Flux<LocalDate> findAllBirthdates();

    @Query("SELECT name FROM clients")
    Flux<String> findAllNames();

    @Query("SELECT last_name FROM clients")
    Flux<String> findAllLastNames();

    @Query("SELECT AVG(age) FROM clients")
    Mono<Double> findAverageAge();

    @Query("SELECT STD(age) FROM clients")
    Mono<Double> findStdAge();

    @Query("SELECT MAX(birth_date) FROM clients")
    Mono<LocalDate> findMaxBirthDate();

    @Query("SELECT MIN(birth_date) FROM clients")
    Mono<LocalDate> findMinBirthDate();

    @Query("SELECT birth_date FROM clients GROUP BY birth_date ORDER BY COUNT(*) DESC LIMIT 1")
    Mono<LocalDate> findModeBirthDate();

}
