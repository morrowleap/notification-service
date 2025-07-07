# Project: SmartNotifier

A pluggable notification service that takes incoming “events” (e.g. user signup, password reset, system alert) and dispatches messages via one or more channels (email, SMS, push, Slack, etc.).


### Roadmap & User Stories
Break your work into six incremental phases—each with a concrete user story that introduces one concept:

#### Phase 1 – Naïve Prototype

**Story 1**  
*As a developer, I want a single `sendAllNotifications(event, channels)` function so that I can dispatch messages for each channel in one place.*

- Write one monstrous method that loops, formats, sends, logs, retries.
- Observe how hard it is to read, test, and extend (monster method + DRY pain).

#### Phase 2 – SRP & DRY Refactor

**Story 2**  
*As a developer, I want to split responsibilities into dedicated classes/modules so that each piece (parsing, formatting, sending, logging, retrying) has exactly one reason to change.*

- Extract `EventParser`, `MessageFormatter`, `ChannelSender`, `Logger`, `RetryPolicy`.
- Centralize template logic to remove copy-paste.

#### Phase 3 – Introduce & Violate Open/Closed Principle (OCP)

**Story 3a (Violation)**
*As a developer, I want a dispatcher with if/elif on channel names so that I can see how adding a new channel forces me to modify existing code.*

* Implement a channel dispatcher that uses if/else blocks to handle different channel types (EMAIL, SMS, PUSH, SLACK).
* Observe how difficult it is to extend the system with new channels without modifying the dispatcher (OCP violation).

**Story 3b (Fixing OCP)**
*As a developer, I want a Notifier interface or abstract class so that I can add new channels by creating new classes without touching the dispatcher.*

* Refactor the dispatcher to use polymorphism (via an interface or abstract class) for the channel handling logic.
* Ensure that new channels can be added by creating new classes implementing the `Notifier` interface, thus adhering to the Open/Closed Principle.



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
