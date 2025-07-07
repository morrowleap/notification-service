package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.SmsFormatter;
import com.example.smartnotifier.service.send.SmsSender;

/**
 * SMS notifier without retry or logging.
 */
public class SmsNotifierNoRetryNoLogging implements Notifier {
    private final SmsFormatter formatter;
    private final SmsSender sender;

    public SmsNotifierNoRetryNoLogging(SmsFormatter formatter, SmsSender sender) {
        this.formatter = formatter;
        this.sender = sender;
    }

    @Override
    public String getChannel() {
        return "SMS";
    }

    @Override
    public void notify(Event event) {
        try {
            String message = formatter.format(event);
            sender.send(event.getRecipientPhone(), message);
        } catch (Exception ignored) {
        }
    }
}
