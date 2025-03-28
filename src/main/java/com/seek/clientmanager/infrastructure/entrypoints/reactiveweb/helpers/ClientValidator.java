package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.helpers;

import com.seek.clientmanager.domain.model.enums.ErrorType;
import com.seek.clientmanager.domain.model.exceptions.DomainException;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.EditClientDTO;
import com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request.NewClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ClientValidator {

    private final String dateRegex = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

    public Mono<NewClientDTO> validateClient(NewClientDTO clientDTO) {
        return Mono.just(clientDTO)
                .filter(c -> c.name() != null && !c.name().isEmpty())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client name cannot be null or empty")))
                .filter(c -> c.age() > 0)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client age must be greater than 0")))
                .filter(c -> c.birthDate() != null && !c.birthDate().isEmpty())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client birthDate cannot be null or empty")))
                .filter(c -> c.birthDate().matches(dateRegex))
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client birthDate must be in YYYY-MM-DD format")));
    }

    public Mono<EditClientDTO> validateClient(EditClientDTO clientDTO) {
        return Mono.just(clientDTO)
                .filter(c -> c.name() != null && !c.name().isEmpty())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client name cannot be null or empty")))
                .filter(c -> c.age() > 0)
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client age must be greater than 0")))
                .filter(c -> c.birthDate() != null && !c.birthDate().isEmpty())
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client birthDate cannot be null or empty")))
                .filter(c -> c.birthDate().matches(dateRegex))
                .switchIfEmpty(Mono.error(new DomainException(ErrorType.INVALID_DATA, "Client birthDate cannot be null or empty")));
    }

}
