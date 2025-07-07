package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.PushFormatter;
import com.example.smartnotifier.service.format.PushFormatter.PushMessage;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.send.PushSender;

/**
 * Push notifier with logging but no retry.
 */
public class PushNotifierNoRetryLogging implements Notifier {
    private final PushFormatter formatter;
    private final PushSender sender;
    private final NotificationLogger logger;

    public PushNotifierNoRetryLogging(PushFormatter formatter, PushSender sender,
            NotificationLogger logger) {
        this.formatter = formatter;
        this.sender = sender;
        this.logger = logger;
    }

    @Override
    public String getChannel() {
        return "PUSH";
    }

    @Override
    public void notify(Event event) {
        try {
            PushMessage message = formatter.format(event);
            sender.send(event.getDeviceToken(), message);
            logger.success(getChannel(), event, 1);
        } catch (Exception e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
