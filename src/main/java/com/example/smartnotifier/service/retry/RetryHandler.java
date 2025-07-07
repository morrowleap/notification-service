package com.example.smartnotifier.service.retry;

import org.springframework.stereotype.Component;

@Component
/**
 * Implements the Template Method pattern for retrying operations. The
 * {@code execute} method defines the algorithm skeleton while the provided
 * {@link RetriableTask} supplies the step that can vary.
 */
public class RetryHandler {
	@FunctionalInterface
	// Command representing the operation to retry
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
