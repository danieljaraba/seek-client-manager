// File: `src/test/java/com/seek/clientmanager/infrastructure/entrypoints/reactiveweb/ClientControllerTest.java`
package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.domain.model.ClientDetail;
import com.seek.clientmanager.domain.usecase.ClientCrudUseCase;
import com.seek.clientmanager.domain.usecase.ClientInfoUseCase;
import com.seek.clientmanager.domain.usecase.ClientsStatsUseCase;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.EditClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response.ClientWithStatsDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.helpers.ClientValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientInfoUseCase clientInfoUseCase;

    @Mock
    private ClientsStatsUseCase clientsStatsUseCase;

    @Mock
    private ClientCrudUseCase clientCrudUseCase;

    @Mock
    private ClientValidator clientValidator;

    @InjectMocks
    private ClientController clientController;

    private Client dummyClient;
    private ClientDTO dummyClientDTO;
    private NewClientDTO newClientDTO;
    private EditClientDTO editClientDTO;
    private ClientDetail dummyClientDetail;
    private ClientWithStatsDTO dummyClientWithStatsDTO;
    private Date dummyDate;

    @BeforeEach
    void setUp() {
        dummyDate = new Date(1000L);

        dummyClient = new Client(1, "Test Client", "test@example.com", 30, dummyDate);
        dummyClientDTO = ClientDTO.fromClient(dummyClient);
        newClientDTO = new NewClientDTO("Test Client", "test@example.com", 30, "2023-01-01");
        editClientDTO = new EditClientDTO(1, "Updated Client", "updated@example.com", 35, "2023-01-01");
        dummyClientDetail = new ClientDetail(1, "Test Client", "test@example.com", 30, dummyDate, 0, 0, 0, 0, 0);
        dummyClientWithStatsDTO = ClientWithStatsDTO.fromClientDetail(dummyClientDetail);
    }

    @Test
    void createClient() {
        when(clientCrudUseCase.createClient(any())).thenReturn(Mono.just(dummyClient));
        when(clientValidator.validateClient((NewClientDTO) any())).thenReturn(Mono.just(newClientDTO));

        Mono<ClientDTO> result = clientController.createClient(newClientDTO);

        StepVerifier.create(result)
                .expectNext(dummyClientDTO)
                .verifyComplete();
    }

    @Test
    void deleteClient() {
        int clientId = 1;
        when(clientCrudUseCase.deleteClient(eq(clientId))).thenReturn(Mono.empty());

        Mono<String> result = clientController.deleteClient(clientId);

        StepVerifier.create(result)
                .expectNext("Client deleted successfully")
                .verifyComplete();
    }

    @Test
    void updateClient() {
        Client updatedClient = new Client(1, "Updated Client", "updated@example.com", 35, dummyDate);
        ClientDTO updatedClientDTO = ClientDTO.fromClient(updatedClient);
        when(clientCrudUseCase.updateClient(any())).thenReturn(Mono.just(updatedClient));
        when(clientValidator.validateClient((EditClientDTO) any())).thenReturn(Mono.just(editClientDTO));

        Mono<ClientDTO> result = clientController.updateClient(editClientDTO);

        StepVerifier.create(result)
                .expectNext(updatedClientDTO)
                .verifyComplete();
    }

    @Test
    void getClientById() {
        int clientId = 1;
        when(clientCrudUseCase.findById(eq(clientId))).thenReturn(Mono.just(dummyClient));

        Mono<ClientDTO> result = clientController.getClientById(clientId);

        StepVerifier.create(result)
                .expectNext(dummyClientDTO)
                .verifyComplete();
    }

    @Test
    void getAllClients() {
        when(clientInfoUseCase.findAllClientDetails()).thenReturn(Flux.just(dummyClientDetail));

        Flux<ClientWithStatsDTO> result = clientController.getAllClients();

        StepVerifier.create(result)
                .expectNext(dummyClientWithStatsDTO)
                .verifyComplete();
    }

    @Test
    void getAgeStd() {
        BigDecimal stdValue = BigDecimal.valueOf(5.5);
        when(clientsStatsUseCase.calculateStdAge()).thenReturn(Mono.just(stdValue));

        Mono<BigDecimal> result = clientController.getAgeStd();

        StepVerifier.create(result)
                .expectNext(stdValue)
                .verifyComplete();
    }

    @Test
    void getAgeAverage() {
        BigDecimal avgValue = BigDecimal.valueOf(30.0);
        when(clientsStatsUseCase.calculateAverageAge()).thenReturn(Mono.just(avgValue));

        Mono<BigDecimal> result = clientController.getAgeAverage();

        StepVerifier.create(result)
                .expectNext(avgValue)
                .verifyComplete();
    }

    @Test
    void getMaxBirthDate() {
        when(clientsStatsUseCase.calculateMaxBirthDate()).thenReturn(Mono.just(dummyDate));

        Mono<String> result = clientController.getMaxBirthDate();

        StepVerifier.create(result)
                .expectNext(dummyDate.toString())
                .verifyComplete();
    }

    @Test
    void getMinBirthDate() {
        when(clientsStatsUseCase.calculateMinBirthDate()).thenReturn(Mono.just(dummyDate));

        Mono<String> result = clientController.getMinBirthDate();

        StepVerifier.create(result)
                .expectNext(dummyDate.toString())
                .verifyComplete();
    }

    @Test
    void getModeBirthDate() {
        when(clientsStatsUseCase.calculateModeBirthDate()).thenReturn(Mono.just(dummyDate));

        Mono<String> result = clientController.getModeBirthDate();

        StepVerifier.create(result)
                .expectNext(dummyDate.toString())
                .verifyComplete();
    }
}