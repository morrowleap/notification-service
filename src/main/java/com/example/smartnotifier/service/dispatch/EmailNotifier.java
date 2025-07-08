package com.example.smartnotifier.service.dispatch;


import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.EmailFormatter;
import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;
import com.example.smartnotifier.service.send.EmailSender;

// Base notifier implementation without cross-cutting concerns
// Concrete strategy that handles email notifications
public class EmailNotifier implements Notifier {
	private final EmailFormatter formatter;
	private final EmailSender sender;

       public EmailNotifier(EmailFormatter formatter, EmailSender sender) {
               this.formatter = formatter;
               this.sender = sender;
       }

	@Override
	public String getChannel() {
		return "EMAIL";
	}

       @Override
       public void notify(Event event) {
               EmailMessage email = formatter.format(event);
               sender.send(event.getRecipientEmail(), email);
       }
}
