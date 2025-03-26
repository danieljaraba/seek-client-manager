package com.seek.clientmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ClientManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientManagerApplication.class, args);
    }

}
