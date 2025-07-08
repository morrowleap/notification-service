package com.example.smartnotifier.service.dispatch;

import com.example.smartnotifier.model.Event;
import com.example.smartnotifier.service.retry.RetryHandler;

/**
 * Decorator that adds retry capability to any Notifier implementation.
 */
public class RetryingNotifier implements Notifier {
    private final Notifier delegate;
    private final RetryHandler retryHandler;
    private final int maxAttempts;

    public RetryingNotifier(Notifier delegate, RetryHandler retryHandler, int maxAttempts) {
        this.delegate = delegate;
        this.retryHandler = retryHandler;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public String getChannel() {
        return delegate.getChannel();
    }

    @Override
    public void notify(Event event) {
        try {
            retryHandler.execute(() -> delegate.notify(event), maxAttempts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
