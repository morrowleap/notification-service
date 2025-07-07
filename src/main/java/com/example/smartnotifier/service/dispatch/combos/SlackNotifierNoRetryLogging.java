package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.SlackFormatter;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.send.SlackSender;

/**
 * Slack notifier with logging but no retry.
 */
public class SlackNotifierNoRetryLogging implements Notifier {
    private final SlackFormatter formatter;
    private final SlackSender sender;
    private final NotificationLogger logger;

    public SlackNotifierNoRetryLogging(SlackFormatter formatter, SlackSender sender,
            NotificationLogger logger) {
        this.formatter = formatter;
        this.sender = sender;
        this.logger = logger;
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
            logger.success(getChannel(), event, 1);
        } catch (Exception e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
