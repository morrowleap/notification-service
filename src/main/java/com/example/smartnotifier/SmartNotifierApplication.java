package com.example.smartnotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SmartNotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartNotifierApplication.class, args);
    }

    @GetMapping("/health")
    public String hello() {
        return "OK";
    }
}
