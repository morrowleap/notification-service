package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.SmsFormatter;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.SmsSender;

/**
 * SMS notifier with retry but no logging.
 */
public class SmsNotifierRetryNoLogging implements Notifier {
    private final SmsFormatter formatter;
    private final SmsSender sender;
    private final RetryHandler retryHandler;

    public SmsNotifierRetryNoLogging(SmsFormatter formatter, SmsSender sender,
            RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
        this.retryHandler = retryHandler;
    }

    @Override
    public String getChannel() {
        return "SMS";
    }

    @Override
    public void notify(Event event) {
        try {
            String message = formatter.format(event);
            retryHandler.execute(() -> {
                sender.send(event.getRecipientPhone(), message);
            }, 3);
        } catch (Exception ignored) {
        }
    }
}
