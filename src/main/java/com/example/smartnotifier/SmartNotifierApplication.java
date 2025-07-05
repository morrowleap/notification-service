package com.example.smartnotifier;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartnotifier.service.NotificationService;

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

	@PostMapping("/event")
	public void sendAllNotifications(NotificationService.Event event, List<String> channels) {

	}
}
