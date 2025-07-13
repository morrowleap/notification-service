package com.example.smartnotifier.service.dispatch;



import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.PushFormatter;
import com.example.smartnotifier.service.format.PushFormatter.PushMessage;
import com.example.smartnotifier.service.send.PushSender;

// Base notifier implementation without cross-cutting concerns
// Concrete strategy that handles push notifications
public class PushNotifier implements Notifier {
	private final PushFormatter formatter;
	private final PushSender sender;

       public PushNotifier(PushFormatter formatter, PushSender sender) {
               this.formatter = formatter;
               this.sender = sender;
       }

	@Override
	public String getChannel() {
		return "PUSH";
	}

	@Override
	public void notify(Event event) {
               PushMessage message = formatter.format(event);
               sender.send(event.getDeviceToken(), message);
       }
}
