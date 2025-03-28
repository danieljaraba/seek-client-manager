package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.request;

import com.seek.clientmanager.domain.model.Client;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public record NewClientDTO(
        String name,
        String lastName,
        int age,
        String birthDate
) {

    public static Client toClient(NewClientDTO newClientDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(newClientDTO.birthDate(), formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new Client(
                0,
                newClientDTO.name(),
                newClientDTO.lastName(),
                newClientDTO.age(),
                date
        );
    }

}
