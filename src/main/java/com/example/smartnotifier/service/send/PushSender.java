package com.example.smartnotifier.service.send;

import org.springframework.stereotype.Component;

import com.example.smartnotifier.service.format.PushFormatter.PushMessage;

@Component
public class PushSender {
	public void send(String deviceToken, PushMessage message) {
		// simulate sending push notification
	}
}
