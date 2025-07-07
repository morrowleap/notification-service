package com.example.smartnotifier.service.retry;

import org.springframework.stereotype.Component;

@Component
public class RetryHandler {
    @FunctionalInterface
    public interface RetriableTask {
        void run() throws Exception;
    }

    /**
     * Executes the task up to {@code maxAttempts} times. Returns the attempt count
     * if successful, otherwise rethrows the last exception.
     */
    public int execute(RetriableTask task, int maxAttempts) throws Exception {
        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                attempt++;
                task.run();
                return attempt;
            } catch (Exception e) {
                if (attempt >= maxAttempts) {
                    throw e;
                }
            }
        }
        return attempt;
    }
}
