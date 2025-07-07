package com.example.smartnotifier.service.dispatch;

import com.example.smartnotifier.model.Event;

/**
 * Strategy interface for all notification channels. Each implementation acts as
 * a concrete strategy that knows how to deliver a message through a specific
 * medium.
 */
public interface Notifier {
	String getChannel();

	void notify(Event event);
}
