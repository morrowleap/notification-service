package com.example.smartnotifier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.NotificationService;

@SpringBootApplication
@RestController
public class SmartNotifierApplication {

       private final NotificationService notificationService;

       @Autowired
       public SmartNotifierApplication(NotificationService notificationService) {
               this.notificationService = notificationService;
       }

	public static void main(String[] args) {
		SpringApplication.run(SmartNotifierApplication.class, args);
	}

	@GetMapping("/health")
	public String hello() {
		return "OK";
	}

       @PostMapping("/event")
       public void sendAllNotifications(@RequestBody Event event,
                       @RequestParam List<String> channels) {
               notificationService.sendAllNotifications(event, channels);
       }
}
