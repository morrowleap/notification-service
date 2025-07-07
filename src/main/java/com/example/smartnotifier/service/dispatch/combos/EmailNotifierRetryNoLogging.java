package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.EmailFormatter;
import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.EmailSender;

/**
 * Email notifier with retry but no logging.
 */
public class EmailNotifierRetryNoLogging implements Notifier {
    private final EmailFormatter formatter;
    private final EmailSender sender;
    private final RetryHandler retryHandler;

    public EmailNotifierRetryNoLogging(EmailFormatter formatter, EmailSender sender,
            RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
        this.retryHandler = retryHandler;
    }

    @Override
    public String getChannel() {
        return "EMAIL";
    }

    @Override
    public void notify(Event event) {
        try {
            EmailMessage email = formatter.format(event);
            retryHandler.execute(() -> {
                sender.send(event.getRecipientEmail(), email);
            }, 3);
        } catch (Exception ignored) {
        }
    }
}
