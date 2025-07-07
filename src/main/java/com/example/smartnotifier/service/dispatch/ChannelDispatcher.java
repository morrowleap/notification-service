package com.example.smartnotifier.service.dispatch;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;

@Component
public class ChannelDispatcher {

    private final Map<String, Notifier> notifierMap;

    @Autowired
    public ChannelDispatcher(List<Notifier> notifiers) {
        // Spring injects all beans that implement the Notifier interface
        // which allows adding new channels without modifying this class
        this.notifierMap = notifiers.stream()
                .collect(Collectors.toMap(Notifier::getChannel, n -> n));
    }

    public void dispatch(Event event, String channel) {
        Notifier notifier = notifierMap.get(channel);
        if (notifier != null) {
            notifier.notify(event);
        } else {
            System.err.println("Unknown channel: " + channel);
        }
    }
}
