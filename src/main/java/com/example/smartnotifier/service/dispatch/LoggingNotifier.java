package com.example.smartnotifier.service.dispatch;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.logging.NotificationLogger;

/**
 * Decorator that logs the outcome of notifications.
 */
public class LoggingNotifier implements Notifier {
    private final Notifier delegate;
    private final NotificationLogger logger;

    public LoggingNotifier(Notifier delegate, NotificationLogger logger) {
        this.delegate = delegate;
        this.logger = logger;
    }

    @Override
    public String getChannel() {
        return delegate.getChannel();
    }

    @Override
    public void notify(Event event) {
        try {
            delegate.notify(event);
            logger.success(getChannel(), event, 1);
        } catch (RuntimeException e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
