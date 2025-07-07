package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.PushFormatter;
import com.example.smartnotifier.service.format.PushFormatter.PushMessage;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.PushSender;

/**
 * Push notifier with retry but no logging.
 */
public class PushNotifierRetryNoLogging implements Notifier {
    private final PushFormatter formatter;
    private final PushSender sender;
    private final RetryHandler retryHandler;

    public PushNotifierRetryNoLogging(PushFormatter formatter, PushSender sender,
            RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
        this.retryHandler = retryHandler;
    }

    @Override
    public String getChannel() {
        return "PUSH";
    }

    @Override
    public void notify(Event event) {
        try {
            PushMessage message = formatter.format(event);
            retryHandler.execute(() -> {
                sender.send(event.getDeviceToken(), message);
            }, 3);
        } catch (Exception ignored) {
        }
    }
}
