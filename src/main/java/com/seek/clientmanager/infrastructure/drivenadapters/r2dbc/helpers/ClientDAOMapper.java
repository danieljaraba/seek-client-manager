package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.helpers;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data.ClientDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface ClientDAOMapper {

    @Mapping(target = "id", source = "id")
    Client toClient(ClientDAO clientDAO);

    @Mapping(target = "id", source = "id")
    ClientDAO toClientDAO(Client client);

    Date toDate(String date);

    String fromDate(Date date);

    BigDecimal toBigDecimal(Double value);

    Double toDouble(BigDecimal value);

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
