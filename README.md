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

