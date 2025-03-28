package com.seek.clientmanager.infrastructure.entrypoints.reactiveweb.data.response;

import com.seek.clientmanager.domain.model.ClientDetail;
import lombok.Builder;

import java.text.SimpleDateFormat;

@Builder
public record ClientWithStatsDTO(
        int id,
        String name,
        String lastName,
        int age,
        String birthDate,
        int daysToBirthday,
        int daysLiving,
        int weeksLiving,
        int monthsLiving,
        int yearsLiving
) {

    public static ClientWithStatsDTO fromClientDetail(ClientDetail clientDetail) {
        return ClientWithStatsDTO.builder()
                .id(clientDetail.id())
                .name(clientDetail.name())
                .lastName(clientDetail.lastName())
                .age(clientDetail.age())
                .birthDate(new SimpleDateFormat("yyyy-MM-dd").format(clientDetail.birthDate()))
                .daysToBirthday(clientDetail.daysToBirthday())
                .daysLiving(clientDetail.daysLiving())
                .weeksLiving(clientDetail.weeksLiving())
                .monthsLiving(clientDetail.monthsLiving())
                .yearsLiving(clientDetail.yearsLiving())
                .build();
    }

}
