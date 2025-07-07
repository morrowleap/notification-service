package com.example.smartnotifier.model;

@lombok.Data
public class Event {
	private String type;
	private String payload;
	private String recipientName;
	private String recipientEmail;
	private String recipientPhone;
	private String deviceToken;
	private String slackChannelId;
}
