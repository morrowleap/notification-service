package com.example.smartnotifier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.smartnotifier.service.dispatch.*;
import com.example.smartnotifier.service.format.*;
import com.example.smartnotifier.service.logging.NotificationLogger;
import com.example.smartnotifier.service.retry.RetryHandler;
import com.example.smartnotifier.service.send.*;

/**
 * Builds decorated {@link Notifier} instances using composition so each cross-cutting
 * concern can be applied at runtime.
 */
@Configuration
public class NotifierConfiguration {

    @Bean
    public Notifier emailNotifier(EmailFormatter formatter, EmailSender sender,
                                  RetryHandler retryHandler, NotificationLogger logger) {
        Notifier base = new EmailNotifier(formatter, sender);
        Notifier withRetry = new RetryingNotifier(base, retryHandler, 3);
        return new LoggingNotifier(withRetry, logger);
    }

    @Bean
    public Notifier smsNotifier(SmsFormatter formatter, SmsSender sender,
                                RetryHandler retryHandler, NotificationLogger logger) {
        Notifier base = new SmsNotifier(formatter, sender);
        Notifier withRetry = new RetryingNotifier(base, retryHandler, 3);
        return new LoggingNotifier(withRetry, logger);
    }

    @Bean
    public Notifier pushNotifier(PushFormatter formatter, PushSender sender,
                                 RetryHandler retryHandler, NotificationLogger logger) {
        Notifier base = new PushNotifier(formatter, sender);
        Notifier withRetry = new RetryingNotifier(base, retryHandler, 3);
        return new LoggingNotifier(withRetry, logger);
    }

    @Bean
    public Notifier slackNotifier(SlackFormatter formatter, SlackSender sender,
                                  RetryHandler retryHandler, NotificationLogger logger) {
        Notifier base = new SlackNotifier(formatter, sender);
        Notifier withRetry = new RetryingNotifier(base, retryHandler, 3);
        return new LoggingNotifier(withRetry, logger);
    }
}
