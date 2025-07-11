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

#### Phase 4 – Explore LSP Violations

**Story 4**
*As a developer, I want to subclass `EmailNotifier` for `SMSNotifier` just to reuse code so that I discover substitution breaks (e.g. `SMSNotifier` doesn’t support attachments) and write a test that fails.*

Refactor to ensure all notifiers honor the same calling contract (e.g. throw on unsupported features).

**Note:** I have not implemented this yet, as it is too complicated right now.


#### Phase 5 – Witness Class Explosion

**Story 5**
*As a developer, I want separate subclasses for every “channel + retry + logging” combo so that I experience the combinatorial explosion of tiny classes.*

https://github.com/morrowleap/smart-notifier/pull/5 This rejected PR, is an example of class-explosion.


#### Phase 6 – Apply Composition Over Inheritance

**Story 6**  
*As a developer, I want wrapper/decorator classes like `RetryingNotifier` and `LoggingNotifier` so that I can compose behavior at runtime and avoid endless subclasses.*

- **Create Decorator Classes**: 
   - `RetryingNotifier`: Wraps a base `Notifier` to add retry functionality.
   - `LoggingNotifier`: Wraps a base `Notifier` to add logging functionality.

- **Composing the Notifiers**:
   ```java
   Notifier email = new EmailNotifier(config);
   Notifier withRetry = new RetryingNotifier(email, 3); // Retry 3 times
   Notifier logged = new LoggingNotifier(withRetry, logger);
   
https://github.com/morrowleap/smart-notifier/pull/6 This PR, shows separation of concerns and class composition
