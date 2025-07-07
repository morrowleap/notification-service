package com.example.smartnotifier.service.format;

import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;

@Component
public class PushFormatter {
    public PushMessage format(Event event) {
        return new PushMessage(event.getType(), event.getPayload());
    }

    public static class PushMessage {
        private final String title;
        private final String content;
        public PushMessage(String title, String content) {
            this.title = title;
            this.content = content;
        }
        public String getTitle() { return title; }
        public String getContent() { return content; }
    }
}
