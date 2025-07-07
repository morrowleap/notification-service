package com.example.smartnotifier.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.format.EmailFormatter;
import com.example.smartnotifier.service.format.EmailFormatter.EmailMessage;
import com.example.smartnotifier.service.format.PushFormatter;
import com.example.smartnotifier.service.format.PushFormatter.PushMessage;
import com.example.smartnotifier.service.format.SlackFormatter;
import com.example.smartnotifier.service.format.SmsFormatter;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.retry.RetryHandler.RetriableTask;
import com.example.smartnotifier.service.send.EmailSender;
import com.example.smartnotifier.service.send.PushSender;
import com.example.smartnotifier.service.send.SlackSender;
import com.example.smartnotifier.service.send.SmsSender;

@Service
public class NotificationService {

    private final EmailFormatter emailFormatter;
    private final SmsFormatter smsFormatter;
    private final PushFormatter pushFormatter;
    private final SlackFormatter slackFormatter;
    private final EmailSender emailSender;
    private final SmsSender smsSender;
    private final PushSender pushSender;
    private final SlackSender slackSender;
    private final NotificationLogger logger;
    private final RetryHandler retryHandler;

    @Autowired
    public NotificationService(
            EmailFormatter emailFormatter,
            SmsFormatter smsFormatter,
            PushFormatter pushFormatter,
            SlackFormatter slackFormatter,
            EmailSender emailSender,
            SmsSender smsSender,
            PushSender pushSender,
            SlackSender slackSender,
            NotificationLogger logger,
            RetryHandler retryHandler) {
        this.emailFormatter = emailFormatter;
        this.smsFormatter = smsFormatter;
        this.pushFormatter = pushFormatter;
        this.slackFormatter = slackFormatter;
        this.emailSender = emailSender;
        this.smsSender = smsSender;
        this.pushSender = pushSender;
        this.slackSender = slackSender;
        this.logger = logger;
        this.retryHandler = retryHandler;
    }

    public void sendAllNotifications(Event event, List<String> channels) {
        for (String channel : channels) {
            try {
                switch (channel) {
                    case "EMAIL":
                        EmailMessage email = emailFormatter.format(event);
                        int attempts = retryHandler.execute(new RetriableTask() {
                            @Override
                            public void run() throws Exception {
                                emailSender.send(event.getRecipientEmail(), email);
                            }
                        }, 3);
                        logger.success(channel, event, attempts);
                        break;
                    case "SMS":
                        String smsMessage = smsFormatter.format(event);
                        smsSender.send(event.getRecipientPhone(), smsMessage);
                        logger.success(channel, event, 1);
                        break;
                    case "PUSH":
                        PushMessage pushMessage = pushFormatter.format(event);
                        pushSender.send(event.getDeviceToken(), pushMessage);
                        logger.success(channel, event, 1);
                        break;
                    case "SLACK":
                        String slackMessage = slackFormatter.format(event);
                        slackSender.send(event.getSlackChannelId(), slackMessage);
                        logger.success(channel, event, 1);
                        break;
                    default:
                        System.err.println("Unknown channel: " + channel);
                        break;
                }
            } catch (Exception e) {
                logger.error(channel, e, 1);
            }
        }
    }
}
