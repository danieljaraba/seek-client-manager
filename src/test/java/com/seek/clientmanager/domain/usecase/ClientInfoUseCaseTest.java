package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.Client;
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
import java.util.Calendar;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class NewClientInfoUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientInfoUseCase clientInfoUseCase;

    private static final Client CLIENT_A = new Client(1, "John", "Doe", 30, new Date(90, Calendar.JANUARY, 1));
    private static final Client CLIENT_B = new Client(2, "Test", "Leap", 20, new Date(100, Calendar.JANUARY, 2));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Clock fixedClock = Clock.fixed(Instant.parse("2020-01-01T00:00:01Z"), ZoneId.systemDefault());
        clientInfoUseCase = new ClientInfoUseCase(clientRepository, fixedClock);
    }

    @Test
    void testSingleClientCalculations() {
        Mockito.when(clientRepository.findAll()).thenReturn(Flux.just(CLIENT_B));
        StepVerifier.create(clientInfoUseCase.findAllClientDetails())
                .assertNext(cd -> {
                    assert cd.daysLiving() == 7304;
                    assert cd.yearsLiving() == 20;
                })
                .verifyComplete();
    }

    @Test
    void testTwoClientsCalculations() {
        Mockito.when(clientRepository.findAll()).thenReturn(Flux.just(CLIENT_A, CLIENT_B));
        StepVerifier.create(clientInfoUseCase.findAllClientDetails())
                .assertNext(cd -> {
                    assert cd.daysLiving() == 10957;
                })
                .assertNext(cd -> {
                    assert cd.daysLiving() == 7304;
                })
                .verifyComplete();
    }
}