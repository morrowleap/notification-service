package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.SlackFormatter;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.SlackSender;

/**
 * Slack notifier with retry and logging.
 */
public class SlackNotifierRetryLogging implements Notifier {
    private final SlackFormatter formatter;
    private final SlackSender sender;
    private final NotificationLogger logger;
    private final RetryHandler retryHandler;

    public SlackNotifierRetryLogging(SlackFormatter formatter, SlackSender sender,
            NotificationLogger logger, RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
        this.logger = logger;
        this.retryHandler = retryHandler;
    }

    @Override
    public String getChannel() {
        return "SLACK";
    }

    @Override
    public void notify(Event event) {
        try {
            String message = formatter.format(event);
            int attempts = retryHandler.execute(() -> {
                sender.send(event.getSlackChannelId(), message);
            }, 3);
            logger.success(getChannel(), event, attempts);
        } catch (Exception e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
