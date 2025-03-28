package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.domain.model.ClientDetail;
import com.seek.clientmanager.domain.model.gateways.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class ClientInfoUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientInfoUseCase clientInfoUseCase;

    private static final Client CLIENT1 = new Client(1, "John", "Doe", 30, new Date(90, Calendar.JANUARY, 1));
    private static final Client CLIENT2 = new Client(2, "Jane", "Doe", 25, new Date(95, Calendar.FEBRUARY, 1));
    private static final Client CLIENT3 = new Client(3, "Alice", "Smith", 35, new Date(85, Calendar.JANUARY, 2));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Clock fixedClock = Clock.fixed(Instant.parse("2020-01-01T00:00:01Z"), ZoneId.systemDefault());
        clientInfoUseCase = new ClientInfoUseCase(clientRepository, fixedClock);
    }

    @Test
    void testClientsDaysToBirthday() {
        Mockito.when(clientRepository.findAll())
                .thenReturn(Flux.fromIterable(Arrays.asList(CLIENT1, CLIENT2, CLIENT3)));
        Flux<ClientDetail> clientDetailsFlux = clientInfoUseCase.findAllClientDetails();
        StepVerifier.create(clientDetailsFlux)
                .expectNextMatches(clientDetail -> clientDetail.daysToBirthday() == 0)
                .expectNextMatches(clientDetail -> clientDetail.daysToBirthday() == 31)
                .expectNextMatches(clientDetail -> clientDetail.daysToBirthday() == 1)
                .verifyComplete();
    }

    @Test
    void testClientsDaysLiving() {
        Mockito.when(clientRepository.findAll())
                .thenReturn(Flux.fromIterable(Arrays.asList(CLIENT1, CLIENT2, CLIENT3)));
        Flux<ClientDetail> clientDetailsFlux = clientInfoUseCase.findAllClientDetails();
        StepVerifier.create(clientDetailsFlux)
                .expectNextMatches(clientDetail -> clientDetail.daysLiving() == 10957)
                .expectNextMatches(clientDetail -> clientDetail.daysLiving() == 9100)
                .expectNextMatches(clientDetail -> clientDetail.daysLiving() == 12782)
                .verifyComplete();
    }

    @Test
    void testClientsWeeksLiving() {
        Mockito.when(clientRepository.findAll())
                .thenReturn(Flux.fromIterable(Arrays.asList(CLIENT1, CLIENT2, CLIENT3)));
        Flux<ClientDetail> clientDetailsFlux = clientInfoUseCase.findAllClientDetails();
        StepVerifier.create(clientDetailsFlux)
                .expectNextMatches(clientDetail -> clientDetail.weeksLiving() == 1566)
                .expectNextMatches(clientDetail -> clientDetail.weeksLiving() == 1300)
                .expectNextMatches(clientDetail -> clientDetail.weeksLiving() == 1826)
                .verifyComplete();
    }

    @Test
    void testClientsMonthsLiving() {
        Mockito.when(clientRepository.findAll())
                .thenReturn(Flux.fromIterable(Arrays.asList(CLIENT1, CLIENT2, CLIENT3)));
        Flux<ClientDetail> clientDetailsFlux = clientInfoUseCase.findAllClientDetails();
        StepVerifier.create(clientDetailsFlux)
                .expectNextMatches(clientDetail -> clientDetail.monthsLiving() == 360)
                .expectNextMatches(clientDetail -> clientDetail.monthsLiving() == 299)
                .expectNextMatches(clientDetail -> clientDetail.monthsLiving() == 420)
                .verifyComplete();
    }

    @Test
    void testClientsYearsLiving() {
        Mockito.when(clientRepository.findAll())
                .thenReturn(Flux.fromIterable(Arrays.asList(CLIENT1, CLIENT2, CLIENT3)));
        Flux<ClientDetail> clientDetailsFlux = clientInfoUseCase.findAllClientDetails();
        StepVerifier.create(clientDetailsFlux)
                .expectNextMatches(clientDetail -> clientDetail.yearsLiving() == 30)
                .expectNextMatches(clientDetail -> clientDetail.yearsLiving() == 25)
                .expectNextMatches(clientDetail -> clientDetail.yearsLiving() == 35)
                .verifyComplete();
    }
}