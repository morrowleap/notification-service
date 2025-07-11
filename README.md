# Project: SmartNotifier

A pluggable notification service that takes incoming “events” (e.g. user signup, password reset, system alert) and dispatches messages via one or more channels (email, SMS, push, Slack, etc.).
For an overview of the project's learning roadmap and detailed user stories, see [ROADMAP.md](ROADMAP.md).

## Requirements

The project is built with **JDK 21** via the Gradle toolchain. Make sure JDK 21
is available on your machine.


## Building and Testing

Run the Gradle wrapper from the project root:

```bash
./gradlew build
./gradlew test
```


## Running the Application

Start the Spring Boot application with:

```bash
./gradlew bootRun
```

The server listens on port `8080` by default.


### Example requests

Check the health endpoint:

```bash
curl --location 'http://localhost:8080/health'
```

Send a test event to multiple channels:

```bash
curl --location 'http://localhost:8080/event?channels=EMAIL%2CSMS%2CPUSH%2CSLACK' \
--header 'Content-Type: application/json' \
--data-raw '{
  "type": "WELCOME",
  "payload": "Welcome to SmartNotifier!",
  "recipientName": "John Doe",
  "recipientEmail": "john@example.com",
  "recipientPhone": "+15551234567",
  "deviceToken": "abcd1234",
  "slackChannelId": "C12345678"
}'
```


## Design Patterns

This project demonstrates several design patterns:

- **Strategy:** `ChannelDispatcher` selects a `Notifier` strategy (EmailNotifier, SmsNotifier, etc.) to deliver messages.
- **Facade:** `NotificationService` exposes a simple method that hides the dispatcher and channel details.
- **Template Method & Command:** `RetryHandler` defines the retry algorithm while each `RetriableTask` command supplies the action to retry.
