package com.example.smartnotifier.service.dispatch;



import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.SlackFormatter;
import com.example.smartnotifier.service.send.SlackSender;

// Base notifier implementation without cross-cutting concerns
// Concrete strategy that handles Slack notifications
public class SlackNotifier implements Notifier {
	private final SlackFormatter formatter;
	private final SlackSender sender;

       public SlackNotifier(SlackFormatter formatter, SlackSender sender) {
               this.formatter = formatter;
               this.sender = sender;
       }

	@Override
	public String getChannel() {
		return "SLACK";
	}

	@Override
	public void notify(Event event) {
               String message = formatter.format(event);
               sender.send(event.getSlackChannelId(), message);
       }
}
