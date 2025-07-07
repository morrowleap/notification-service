package com.example.smartnotifier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.dispatch.ChannelDispatcher;

@Service
public class NotificationService {

        private final ChannelDispatcher dispatcher;

        @Autowired
        public NotificationService(ChannelDispatcher dispatcher) {
                this.dispatcher = dispatcher;
        }

	public void sendAllNotifications(Event event, List<String> channels) {
                for (String channel : channels) {
                        dispatcher.dispatch(event, channel);
                }
	}
}
