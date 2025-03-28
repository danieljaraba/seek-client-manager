package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response;

import com.seek.clientmanager.domain.model.Client;

import java.text.SimpleDateFormat;

public record ClientDTO(
        int id,
        String name,
        String lastName,
        int age,
        String birthDate
) {

    public static ClientDTO fromClient(Client client) {
        return new ClientDTO(
                client.id(),
                client.name(),
                client.lastName(),
                client.age(),
                new SimpleDateFormat("yyyy-MM-dd").format(client.birthDate())
        );
    }

}
