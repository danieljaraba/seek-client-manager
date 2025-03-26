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
        return clientRepository.save(client);
    }

    public Mono<Client> findById(String id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found")));
    }

    public Mono<Client> updateClient(Client client) {
        return clientRepository.findById(client.id())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found")))
                .flatMap(existingClient -> clientRepository.update(client));
    }

    public Mono<Void> deleteClient(String id) {
        return clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.NOT_FOUND, "Client not found")))
                .flatMap(existingClient -> clientRepository.deleteById(id));
    }

}
