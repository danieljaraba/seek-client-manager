package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.domain.model.gateways.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ClientCrudUseCase {

    private final ClientRepository clientRepository;

    public Mono<Client> createClient(Client client) {
        return Mono.just(client)
                    .flatMap(this::validateClient)
                    .flatMap(clientRepository::save);
    }

    public Mono<Client> findById(int id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found")));
    }

    public Mono<Client> updateClient(Client client) {
        return clientRepository.findById(client.id())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found")))
                .flatMap(existingClient -> clientRepository.update(client));
    }

    public Mono<Void> deleteClient(int id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found")))
                .flatMap(existingClient -> clientRepository.deleteById(id));
    }

    private Mono<Client> validateClient(Client client) {
        return Mono.just(client)
                .filter(c -> c.name()!= null && !c.name().isEmpty())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "First name is required")))
                .flatMap(c -> Mono.just(c)
                        .filter(c1 -> c1.lastName() != null && !c1.lastName().isEmpty())
                        .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Last name is required"))));
    }

}
