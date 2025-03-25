package com.seek.clientmanager.domain.usecase;

import com.seek.clientmanager.domain.model.Client;
import com.seek.clientmanager.domain.model.ClientDetail;
import com.seek.clientmanager.domain.model.gateways.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Service
public class ClientInfoUseCase {

    private final ClientRepository clientRepository;
    private final Clock clock;

    public Flux<ClientDetail> findAllClientDetails() {
        return findAll()
                .flatMap(this::mapToClientDetails)
                .flatMap(this::calculateDaysToBirthday)
                .flatMap(this::calculateDaysLiving)
                .flatMap(this::calculateWeeksLiving)
                .flatMap(this::calculateMonthsLiving)
                .flatMap(this::calculateYearsLiving);
    }

    private Flux<Client> findAll() {
        return clientRepository.findAll();
    }

    private Mono<ClientDetail> mapToClientDetails(Client client) {
        return Mono.just(
                ClientDetail.builder()
                        .build()
                        .fromClient(client)
        );
    }

    private Mono<ClientDetail> calculateDaysToBirthday(ClientDetail clientDetail) {
        return Mono.fromCallable(() -> {
            LocalDate currentDate = LocalDate.now(clock);
            LocalDate birthDate = clientDetail.birthDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate nextBirthday = birthDate.withYear(currentDate.getYear());
            if (!nextBirthday.isAfter(currentDate)) {
                nextBirthday = nextBirthday.plusYears(1);
            }
            long daysToBirthday = ChronoUnit.DAYS.between(currentDate, nextBirthday) - 1;
            return clientDetail.withDaysToBirthday((int) daysToBirthday);
        });
    }

    private Mono<ClientDetail> calculateDaysLiving(ClientDetail clientDetail) {
        return Mono.fromCallable(() -> {
            LocalDate currentDate = LocalDate.now(clock);
            LocalDate birthDate = clientDetail.birthDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            long daysLiving = ChronoUnit.DAYS.between(birthDate, currentDate) + 1;
            return clientDetail.withDaysLiving((int) daysLiving);
        });
    }

    private Mono<ClientDetail> calculateWeeksLiving(ClientDetail clientDetail) {
        return Mono.fromCallable(() -> {
            LocalDate currentDate = LocalDate.now(clock);
            LocalDate birthDate = clientDetail.birthDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            long weeksLiving = ChronoUnit.WEEKS.between(birthDate, currentDate) + 1;
            return clientDetail.withWeeksLiving((int) weeksLiving);
        });
    }

    private Mono<ClientDetail> calculateMonthsLiving(ClientDetail clientDetail) {
        return Mono.fromCallable(() -> {
            LocalDate currentDate = LocalDate.now(clock);
            LocalDate birthDate = clientDetail.birthDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            long monthsLiving = ChronoUnit.MONTHS.between(birthDate, currentDate) + 1;
            return clientDetail.withMonthsLiving((int) monthsLiving);
        });
    }

    private Mono<ClientDetail> calculateYearsLiving(ClientDetail clientDetail) {
        return Mono.fromCallable(() -> {
            LocalDate currentDate = LocalDate.now(clock);
            LocalDate birthDate = clientDetail.birthDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            long yearsLiving = ChronoUnit.YEARS.between(birthDate, currentDate) + 1;
            return clientDetail.withYearsLiving((int) yearsLiving);
        });
    }
}
