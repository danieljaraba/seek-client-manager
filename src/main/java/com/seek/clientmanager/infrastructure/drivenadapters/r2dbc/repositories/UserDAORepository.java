package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.repositories;

import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data.UserDAO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserDAORepository extends ReactiveCrudRepository<UserDAO, UUID> {

    @Query("SELECT * FROM users WHERE email = :email")
    Mono<UserDAO> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM users WHERE email = :email")
    Mono<Boolean> existsByEmail(String email);

    @Query("SELECT password FROM users WHERE email = :email")
    Mono<String> findPasswordByEmail(String email);
}
