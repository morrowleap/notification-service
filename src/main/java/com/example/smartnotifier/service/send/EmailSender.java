package com.example.smartnotifier.service.send;

import org.springframework.stereotype.Component;

import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;

@Component
public class EmailSender {
    public void send(String to, EmailMessage message) {
        // simulate sending email
    }
}
