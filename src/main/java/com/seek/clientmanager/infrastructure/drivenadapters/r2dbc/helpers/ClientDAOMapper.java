package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.helpers;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data.ClientDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ClientDAOMapper {

    Client toClient(ClientDAO clientDAO);

    ClientDAO toClientDAO(Client client);

    default Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    default LocalDate fromDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    default BigDecimal toBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }

    default Double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }

    default Mono<Client> toClient(Mono<ClientDAO> clientDAO) {
        return clientDAO.map(this::toClient);
    }

    default Mono<ClientDAO> toClientDAO(Mono<Client> client) {
        return client.map(this::toClientDAO);
    }

    default Flux<Client> toClient(Flux<ClientDAO> clientDAO) {
        return clientDAO.map(this::toClient);
    }

    default Flux<ClientDAO> toClientDAO(Flux<Client> client) {
        return client.map(this::toClientDAO);
    }

}
