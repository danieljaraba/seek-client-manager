package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.domain.model.gateways.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientCrudUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientCrudUseCase clientCrudUseCase;

    private Client dummyClient;

    @BeforeEach
    void setUp() {
        dummyClient = new Client(1, "Test", "User", 30, null);
    }

    @Test
    void createClient() {
        when(clientRepository.save(any(Client.class)))
                .thenReturn(Mono.just(dummyClient));

        Mono<Client> result = clientCrudUseCase.createClient(dummyClient);

        StepVerifier.create(result)
                .expectNext(dummyClient)
                .verifyComplete();
    }

    @Test
    void findById_found() {
        when(clientRepository.findById(dummyClient.id()))
                .thenReturn(Mono.just(dummyClient));

        Mono<Client> result = clientCrudUseCase.findById(dummyClient.id());

        StepVerifier.create(result)
                .expectNext(dummyClient)
                .verifyComplete();
    }

    @Test
    void findById_notFound() {
        when(clientRepository.findById(dummyClient.id()))
                .thenReturn(Mono.empty());

        Mono<Client> result = clientCrudUseCase.findById(dummyClient.id());

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("Client not found")
                )
                .verify();
    }

    @Test
    void updateClient_found() {
        when(clientRepository.findById(dummyClient.id()))
                .thenReturn(Mono.just(dummyClient));
        when(clientRepository.update(any(Client.class)))
                .thenReturn(Mono.just(dummyClient));

        Mono<Client> result = clientCrudUseCase.updateClient(dummyClient);

        StepVerifier.create(result)
                .expectNext(dummyClient)
                .verifyComplete();
    }

    @Test
    void updateClient_notFound() {
        when(clientRepository.findById(dummyClient.id()))
                .thenReturn(Mono.empty());

        Mono<Client> result = clientCrudUseCase.updateClient(dummyClient);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("Client not found")
                )
                .verify();
    }

    @Test
    void deleteClient_found() {
        when(clientRepository.findById(dummyClient.id()))
                .thenReturn(Mono.just(dummyClient));
        when(clientRepository.deleteById(dummyClient.id()))
                .thenReturn(Mono.empty());

        Mono<Void> result = clientCrudUseCase.deleteClient(dummyClient.id());

        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void deleteClient_notFound() {
        when(clientRepository.findById(dummyClient.id()))
                .thenReturn(Mono.empty());

        Mono<Void> result = clientCrudUseCase.deleteClient(dummyClient.id());

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof DomainException &&
                                ((DomainException) throwable).getErrorType() == ErrorType.NOT_FOUND &&
                                throwable.getMessage().contains("Client not found")
                )
                .verify();
    }
}