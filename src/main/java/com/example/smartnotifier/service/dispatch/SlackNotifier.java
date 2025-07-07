package com.example.smartnotifier.service.dispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.SlackFormatter;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.send.SlackSender;

@Component
public class SlackNotifier implements Notifier {
    private final SlackFormatter formatter;
    private final SlackSender sender;
    private final NotificationLogger logger;

    @Autowired
    public SlackNotifier(SlackFormatter formatter, SlackSender sender, NotificationLogger logger) {
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
