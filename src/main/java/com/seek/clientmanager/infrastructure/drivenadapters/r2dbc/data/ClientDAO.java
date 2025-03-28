package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table("clients")
public class ClientDAO {

    @Id
    private int id;
    private String name;
    private String lastName;
    private int age;
    private LocalDate birthDate;

}
