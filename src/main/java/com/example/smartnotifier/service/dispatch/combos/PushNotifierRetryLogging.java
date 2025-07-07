package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.PushFormatter;
import com.example.smartnotifier.service.format.PushFormatter.PushMessage;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.PushSender;

/**
 * Push notifier with retry and logging.
 */
public class PushNotifierRetryLogging implements Notifier {
    private final PushFormatter formatter;
    private final PushSender sender;
    private final NotificationLogger logger;
    private final RetryHandler retryHandler;

    public PushNotifierRetryLogging(PushFormatter formatter, PushSender sender,
            NotificationLogger logger, RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
        this.logger = logger;
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
            int attempts = retryHandler.execute(() -> {
                sender.send(event.getDeviceToken(), message);
            }, 3);
            logger.success(getChannel(), event, attempts);
        } catch (Exception e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
