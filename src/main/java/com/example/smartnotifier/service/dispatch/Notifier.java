package com.example.smartnotifier.service.dispatch;

import com.example.smartnotifier.model.Event;

public interface Notifier {
    String getChannel();
    void notify(Event event);
}
