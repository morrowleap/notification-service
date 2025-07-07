package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.EmailFormatter;
import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.EmailSender;

/**
 * Email notifier with both retry and logging enabled.
 */
public class EmailNotifierRetryLogging implements Notifier {
    private final EmailFormatter formatter;
    private final EmailSender sender;
    private final NotificationLogger logger;
    private final RetryHandler retryHandler;

    public EmailNotifierRetryLogging(EmailFormatter formatter, EmailSender sender,
            NotificationLogger logger, RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
        this.logger = logger;
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
            int attempts = retryHandler.execute(() -> {
                sender.send(event.getRecipientEmail(), email);
            }, 3);
            logger.success(getChannel(), event, attempts);
        } catch (Exception e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
