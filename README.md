# Project: SmartNotifier

A pluggable notification service that takes incoming “events” (e.g. user signup, password reset, system alert) and dispatches messages via one or more channels (email, SMS, push, Slack, etc.).



### Roadmap & User Stories
Break your work into six incremental phases—each with a concrete user story that introduces one concept:

Phase 1 – Naïve Prototype
Story 1

As a developer,
I want a single sendAllNotifications(event, channels) function
So that I can dispatch messages for each channel in one place.

Write one monstrous method that loops, formats, sends, logs, retries.

Observe how hard it is to read, test, and extend (monster method + DRY pain).



Phase 2 – SRP & DRY Refactor
Story 2

As a developer,
I want to split responsibilities into dedicated classes/modules
So that each piece (parsing, formatting, sending, logging, retrying) has exactly one reason to change.

Extract EventParser, MessageFormatter, ChannelSender, Logger, RetryPolicy.

Centralize template logic to remove copy-paste.

