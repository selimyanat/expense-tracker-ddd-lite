package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

/**
 * Persistence model for an event stored in the event store.
 */
@Value
@Builder
public class StoredEvent {

  private final long id;

  private final String eventId;

  private final String aggregateId;

  private final Instant occurredOn;

  private final String typeName;

  private final String payload;


}
