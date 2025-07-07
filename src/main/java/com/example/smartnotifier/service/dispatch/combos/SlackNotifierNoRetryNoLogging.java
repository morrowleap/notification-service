package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.SlackFormatter;
import com.example.smartnotifier.service.send.SlackSender;

/**
 * Slack notifier without retry or logging.
 */
public class SlackNotifierNoRetryNoLogging implements Notifier {
    private final SlackFormatter formatter;
    private final SlackSender sender;

    public SlackNotifierNoRetryNoLogging(SlackFormatter formatter, SlackSender sender) {
        this.formatter = formatter;
        this.sender = sender;
    }

    @Override
    public String getChannel() {
        return "SLACK";
    }

    @Override
    public void notify(Event event) {
        try {
            String message = formatter.format(event);
            sender.send(event.getSlackChannelId(), message);
        } catch (Exception ignored) {
        }
    }
}
