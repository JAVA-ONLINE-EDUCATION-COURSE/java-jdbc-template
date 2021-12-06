package com.epam.izh.rd.online.autcion;

import com.epam.izh.rd.online.autcion.configuration.JdbcTemplateConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                JdbcTemplateConfiguration.class
        );

        SpringApplication.run(Main.class, args);
    }
}

