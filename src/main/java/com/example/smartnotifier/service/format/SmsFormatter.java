package com.example.smartnotifier.service.format;

import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;

@Component
public class SmsFormatter {
	public String format(Event event) {
		return event.getType() + ": " + event.getPayload();
	}
}
