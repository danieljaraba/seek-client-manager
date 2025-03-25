package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("clients")
public class ClientDAO {

    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String lastName;
    private int age;
    private String birthDate;

}
