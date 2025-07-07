package com.example.smartnotifier.service.dispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.SmsFormatter;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.send.SmsSender;

@Component
// Concrete strategy that handles SMS notifications
public class SmsNotifier implements Notifier {
	private final SmsFormatter formatter;
	private final SmsSender sender;
	private final NotificationLogger logger;

	@Autowired
	public SmsNotifier(SmsFormatter formatter, SmsSender sender, NotificationLogger logger) {
		this.formatter = formatter;
		this.sender = sender;
		this.logger = logger;
	}

	@Override
	public String getChannel() {
		return "SMS";
	}

	@Override
	public void notify(Event event) {
		try {
			String message = formatter.format(event);
			sender.send(event.getRecipientPhone(), message);
			logger.success(getChannel(), event, 1);
		} catch (Exception e) {
			logger.error(getChannel(), e, 1);
		}
	}
}
