package com.gestion.clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gestion.clientes")
public class GestionClientesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionClientesBackendApplication.class, args);
    }
}