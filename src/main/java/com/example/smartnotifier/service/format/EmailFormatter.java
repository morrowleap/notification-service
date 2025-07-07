package com.example.smartnotifier.service.format;

import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;

@Component
public class EmailFormatter {
    public EmailMessage format(Event event) {
        String subject = "[SmartNotifier] " + event.getType() + " Notification";
        String body = "Hello " + event.getRecipientName() + ",\n" +
                "You have a new " + event.getType() + " event:\n" +
                event.getPayload() + "\n" +
                "Regards,\nSmartNotifier Team";
        return new EmailMessage(subject, body);
    }

    public static class EmailMessage {
        private final String subject;
        private final String body;
        public EmailMessage(String subject, String body) {
            this.subject = subject;
            this.body = body;
        }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
    }
}
