package com.seek.clientmanager.domain.model;

import lombok.Builder;

import java.util.Date;

@Builder
public record ClientDetail(
        int id,
        String name,
        String lastName,
        int age,
        Date birthDate,
        int daysToBirthday,
        int daysLiving,
        int weeksLiving,
        int monthsLiving,
        int yearsLiving
) {

    public ClientDetail fromClient(Client client) {
        return new ClientDetail(
                client.id(),
                client.name(),
                client.lastName(),
                client.age(),
                client.birthDate(),
                0,
                0,
                0,
                0,
                0
        );
    }

    public ClientDetail withDaysToBirthday(int daysToBirthday) {
        return new ClientDetail(
                id(),
                name(),
                lastName(),
                age(),
                birthDate(),
                daysToBirthday,
                daysLiving,
                weeksLiving,
                monthsLiving,
                yearsLiving
        );
    }

    public ClientDetail withDaysLiving(int daysLiving) {
        return new ClientDetail(
                id(),
                name(),
                lastName(),
                age(),
                birthDate(),
                daysToBirthday,
                daysLiving,
                weeksLiving,
                monthsLiving,
                yearsLiving
        );
    }

    public ClientDetail withWeeksLiving(int weeksLiving) {
        return new ClientDetail(
                id(),
                name(),
                lastName(),
                age(),
                birthDate(),
                daysToBirthday,
                daysLiving,
                weeksLiving,
                monthsLiving,
                yearsLiving
        );
    }

    public ClientDetail withMonthsLiving(int monthsLiving) {
        return new ClientDetail(
                id(),
                name(),
                lastName(),
                age(),
                birthDate(),
                daysToBirthday,
                daysLiving,
                weeksLiving,
                monthsLiving,
                yearsLiving
        );
    }

    public ClientDetail withYearsLiving(int yearsLiving) {
        return new ClientDetail(
                id(),
                name(),
                lastName(),
                age(),
                birthDate(),
                daysToBirthday,
                daysLiving,
                weeksLiving,
                monthsLiving,
                yearsLiving
        );
    }

}
