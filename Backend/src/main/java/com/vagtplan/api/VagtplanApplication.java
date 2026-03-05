package com.vagtplan.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class VagtplanApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(VagtplanApplication.class, args);
    }

}
