package com.example.smartnotifier.service;

import java.util.List;

public class NotificationService {

	public void sendAllNotifications(Event event, List<String> channels) {
		for (String channel : channels) {
			if ("EMAIL".equals(channel)) {
				// Format email
				String subject = "[SmartNotifier] " + event.getType() + " Notification";
				String body = "Hello " + event.getRecipientName() + ",\n" + "You have a new " + event.getType()
						+ " event:\n" + event.getPayload() + "\n" + "Regards,\nSmartNotifier Team";
				// Send email with retry
				int emailAttempts = 0;
				while (emailAttempts < 3) {
					try {
						emailAttempts++;
						EmailClient.send(event.getRecipientEmail(), subject, body);
						System.out
								.println("Email sent to " + event.getRecipientEmail() + " on attempt " + emailAttempts);
						break;
					} catch (Exception e) {
						System.err.println("Failed to send email on attempt " + emailAttempts + ": " + e.getMessage());
						if (emailAttempts >= 3) {
							System.err.println("Giving up on email after 3 attempts");
						}
					}
				}
			} else if ("SMS".equals(channel)) {
				// Format SMS
				String message = event.getType() + ": " + event.getPayload();
				// Send SMS (no retry)
				try {
					SmsClient.send(event.getRecipientPhone(), message);
					System.out.println("SMS sent to " + event.getRecipientPhone());
				} catch (Exception e) {
					System.err.println("Failed to send SMS: " + e.getMessage());
				}
			} else if ("PUSH".equals(channel)) {
				// Format push notification
				String title = event.getType();
				String content = event.getPayload();
				// Send push notification
				try {
					PushClient.send(event.getDeviceToken(), title, content);
					System.out.println("Push notification sent to " + event.getDeviceToken());
				} catch (Exception e) {
					System.err.println("Failed to send push notification: " + e.getMessage());
				}
			} else if ("SLACK".equals(channel)) {
				// Format Slack message
				String slackMessage = "*" + event.getType() + "* - " + event.getPayload();
				// Send to Slack channel
				try {
					SlackClient.post(event.getSlackChannelId(), slackMessage);
					System.out.println("Slack message posted to channel " + event.getSlackChannelId());
				} catch (Exception e) {
					System.err.println("Failed to post Slack message: " + e.getMessage());
				}
			} else {
				System.err.println("Unknown channel: " + channel);
			}
			// Common logging (but duplicated across branches)
			System.out.println("Dispatched " + channel + " notification for event " + event.getType());
		}
	}

	// Dummy client placeholders
	static class EmailClient {
		static void send(String to, String subject, String body) {
			// simulate sending email
		}
	}

	static class SmsClient {
		static void send(String phoneNumber, String message) {
			// simulate sending SMS
		}
	}

	static class PushClient {
		static void send(String deviceToken, String title, String content) {
			// simulate sending push notification
		}
	}

	static class SlackClient {
		static void post(String channelId, String message) {
			// simulate posting to Slack
		}
	}

	// Event placeholder
	public static class Event {
		private String type;
		private String payload;
		private String recipientName;
		private String recipientEmail;
		private String recipientPhone;
		private String deviceToken;
		private String slackChannelId;

		// getters and setters omitted for brevity
		public String getType() {
			return type;
		}

		public String getPayload() {
			return payload;
		}

		public String getRecipientName() {
			return recipientName;
		}

		public String getRecipientEmail() {
			return recipientEmail;
		}

		public String getRecipientPhone() {
			return recipientPhone;
		}

		public String getDeviceToken() {
			return deviceToken;
		}

		public String getSlackChannelId() {
			return slackChannelId;
		}
	}
}
