package com.example.smartnotifier.service.dispatch;



import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.SmsFormatter;
import com.example.smartnotifier.service.send.SmsSender;

// Base notifier implementation without cross-cutting concerns
// Concrete strategy that handles SMS notifications
public class SmsNotifier implements Notifier {
	private final SmsFormatter formatter;
	private final SmsSender sender;

       public SmsNotifier(SmsFormatter formatter, SmsSender sender) {
               this.formatter = formatter;
               this.sender = sender;
       }

	@Override
	public String getChannel() {
		return "SMS";
	}

	@Override
	public void notify(Event event) {
               String message = formatter.format(event);
               sender.send(event.getRecipientPhone(), message);
       }
}
