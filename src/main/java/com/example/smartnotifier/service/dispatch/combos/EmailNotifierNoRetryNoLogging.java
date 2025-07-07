package com.example.smartnotifier.service.dispatch.combos;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.Notifier;
import com.example.smartnotifier.service.format.EmailFormatter;
import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;
import com.example.smartnotifier.service.send.EmailSender;

/**
 * Email notifier with neither retry nor logging.
 */
public class EmailNotifierNoRetryNoLogging implements Notifier {
    private final EmailFormatter formatter;
    private final EmailSender sender;

    public EmailNotifierNoRetryNoLogging(EmailFormatter formatter, EmailSender sender) {
        this.formatter = formatter;
        this.sender = sender;
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
        } catch (Exception ignored) {
        }
    }
}
