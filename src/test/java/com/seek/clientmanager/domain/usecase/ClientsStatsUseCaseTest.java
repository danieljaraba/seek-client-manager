package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.gateways.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class ClientsStatsUseCaseTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientsStatsUseCase clientsStatsUseCase;

    private static final BigDecimal AVERAGE_AGE = new BigDecimal("30.0");
    private static final BigDecimal STD_AGE = new BigDecimal("5.0");
    private static final Date MAX_BIRTH_DATE = new Date(90, 0, 1);
    private static final Date MIN_BIRTH_DATE = new Date(85, 0, 2);
    private static final Date MODE_BIRTH_DATE = new Date(90, 0, 1);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientsStatsUseCase = new ClientsStatsUseCase(clientRepository);
    }

    @Test
    void testCalculateAverageAge() {
        Mockito.when(clientRepository.findAverageAge()).thenReturn(Mono.just(AVERAGE_AGE));

        Mono<BigDecimal> averageAgeMono = clientsStatsUseCase.calculateAverageAge();

        averageAgeMono
                .as(StepVerifier::create)
                .expectNext(AVERAGE_AGE)
                .verifyComplete();
    }

    @Test
    void testCalculateStdAge() {
        Mockito.when(clientRepository.findStdAge()).thenReturn(Mono.just(STD_AGE));

        Mono<BigDecimal> stdAgeMono = clientsStatsUseCase.calculateStdAge();

        stdAgeMono
                .as(StepVerifier::create)
                .expectNext(STD_AGE)
                .verifyComplete();
    }

    @Test
    void testCalculateMaxBirthDate() {
        Mockito.when(clientRepository.findMaxBirthDate()).thenReturn(Mono.just(MAX_BIRTH_DATE));

        Mono<Date> maxBirthDateMono = clientsStatsUseCase.calculateMaxBirthDate();

        maxBirthDateMono
                .as(StepVerifier::create)
                .expectNext(MAX_BIRTH_DATE)
                .verifyComplete();
    }

    @Test
    void testCalculateMinBirthDate() {
        Mockito.when(clientRepository.findMinBirthDate()).thenReturn(Mono.just(MIN_BIRTH_DATE));

        Mono<Date> minBirthDateMono = clientsStatsUseCase.calculateMinBirthDate();

        minBirthDateMono
                .as(StepVerifier::create)
                .expectNext(MIN_BIRTH_DATE)
                .verifyComplete();
    }

    @Test
    void testCalculateModeBirthDate() {
        Mockito.when(clientRepository.findModeBirthDate()).thenReturn(Mono.just(MODE_BIRTH_DATE));

        Mono<Date> modeBirthDateMono = clientsStatsUseCase.calculateModeBirthDate();

        modeBirthDateMono
                .as(StepVerifier::create)
                .expectNext(MODE_BIRTH_DATE)
                .verifyComplete();
    }

}
