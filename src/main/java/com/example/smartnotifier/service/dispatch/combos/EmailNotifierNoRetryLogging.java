package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.EmailFormatter;
import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.send.EmailSender;

/**
 * Email notifier with logging but no retry.
 */
public class EmailNotifierNoRetryLogging implements Notifier {
    private final EmailFormatter formatter;
    private final EmailSender sender;
    private final NotificationLogger logger;

    public EmailNotifierNoRetryLogging(EmailFormatter formatter, EmailSender sender,
            NotificationLogger logger) {
        this.formatter = formatter;
        this.sender = sender;
        this.logger = logger;
    }

    @Override
    public String getChannel() {
        return "EMAIL";
    }

    @Override
    public void notify(Event event) {
        try {
            EmailMessage email = formatter.format(event);
            sender.send(event.getRecipientEmail(), email);
            logger.success(getChannel(), event, 1);
        } catch (Exception e) {
            logger.error(getChannel(), e, 1);
        }
    }
}
