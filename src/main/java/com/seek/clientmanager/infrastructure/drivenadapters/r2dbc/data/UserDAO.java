package com.seek.clientmanager.infrastructure.drivenadapters.r2dbc.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class UserDAO {

    @Id
    private String id;
    private String email;
    private String password;
    private String role;

}
