package com.example.apipersona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiDocumentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDocumentoApplication.class, args);
    }

}
