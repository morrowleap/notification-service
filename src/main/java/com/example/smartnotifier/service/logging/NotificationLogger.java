package com.example.smartnotifier.service.logging;

import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;

@Component
public class NotificationLogger {
	public void success(String channel, Event event, int attempt) {
		System.out.println(
				"Dispatched " + channel + " notification for event " + event.getType() + " on attempt " + attempt);
	}

	public void error(String channel, Exception e, int attempt) {
		System.err.println("Failed to send " + channel + " notification on attempt " + attempt + ": " + e.getMessage());
	}
}
