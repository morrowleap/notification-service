package com.example.smartnotifier.service.dispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.EmailFormatter;
import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.EmailSender;

@Component
// Concrete strategy that handles email notifications
public class EmailNotifier implements Notifier {
	private final EmailFormatter formatter;
	private final EmailSender sender;
	private final NotificationLogger logger;
	private final RetryHandler retryHandler;

	@Autowired
	public EmailNotifier(EmailFormatter formatter, EmailSender sender, NotificationLogger logger,
			RetryHandler retryHandler) {
		this.formatter = formatter;
		this.sender = sender;
		this.logger = logger;
		this.retryHandler = retryHandler;
	}

	@Override
	public String getChannel() {
		return "EMAIL";
	}

	@Override
	public void notify(Event event) {
		try {
			EmailMessage email = formatter.format(event);
			// Command object passed to RetryHandler which executes the task using
			// the Template Method pattern
			int attempts = retryHandler.execute(new RetryHandler.RetriableTask() {
				@Override
				public void run() throws Exception {
					sender.send(event.getRecipientEmail(), email);
				}
			}, 3); // RetriableTask is a functional interface, so a lambda could be used here
			logger.success(getChannel(), event, attempts);
		} catch (Exception e) {
			logger.error(getChannel(), e, 1);
		}
	}
}
