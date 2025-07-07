package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.SlackFormatter;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.SlackSender;

/**
 * Slack notifier with retry but no logging.
 */
public class SlackNotifierRetryNoLogging implements Notifier {
    private final SlackFormatter formatter;
    private final SlackSender sender;
    private final RetryHandler retryHandler;

    public SlackNotifierRetryNoLogging(SlackFormatter formatter, SlackSender sender,
            RetryHandler retryHandler) {
        this.formatter = formatter;
        this.sender = sender;
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
            retryHandler.execute(() -> {
                sender.send(event.getSlackChannelId(), message);
            }, 3);
        } catch (Exception ignored) {
        }
    }
}
