package ru.golovkov.fintracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FintrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintrackerApplication.class, args);
    }
}
