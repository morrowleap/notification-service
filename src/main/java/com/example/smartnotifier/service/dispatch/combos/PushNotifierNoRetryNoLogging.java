package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.PushFormatter;
import com.example.smartnotifier.service.format.PushFormatter.PushMessage;
import com.example.smartnotifier.service.send.PushSender;

/**
 * Push notifier without retry or logging.
 */
public class PushNotifierNoRetryNoLogging implements Notifier {
    private final PushFormatter formatter;
    private final PushSender sender;

    public PushNotifierNoRetryNoLogging(PushFormatter formatter, PushSender sender) {
        this.formatter = formatter;
        this.sender = sender;
    }

    @Override
    public String getChannel() {
        return "PUSH";
    }

    @Override
    public void notify(Event event) {
        try {
            PushMessage message = formatter.format(event);
            sender.send(event.getDeviceToken(), message);
        } catch (Exception ignored) {
        }
    }
}
