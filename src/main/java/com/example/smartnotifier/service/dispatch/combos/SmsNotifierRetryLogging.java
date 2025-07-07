package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.SmsFormatter;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.SmsSender;

/**
 * SMS notifier with retry and logging.
 */
public class SmsNotifierRetryLogging implements Notifier {
    private final SmsFormatter formatter;
    private final SmsSender sender;
    private final NotificationLogger logger;
    private final RetryHandler retryHandler;

    public SmsNotifierRetryLogging(SmsFormatter formatter, SmsSender sender,
            NotificationLogger logger, RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
        this.logger = logger;
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
            int attempts = retryHandler.execute(() -> {
                sender.send(event.getRecipientPhone(), message);
            }, 3);
            logger.success(getChannel(), event, attempts);
        } catch (Exception e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
