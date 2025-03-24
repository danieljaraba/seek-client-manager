package com.seek.clientmanager.domain.model;

import java.util.Date;

public record Client(
        String id,
        String name,
        String lastName,
        int age,
        Date birthDate
) {
}
