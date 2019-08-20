package com.sy.expense.tracker.sharedkernel.events;

import com.sy.expense.tracker.sharedkernel.domain.AggregateId;
import java.time.Instant;
import java.util.UUID;

/**
 * Return prepared instance of {@link DomainEvent} to use during testing.
 */
public final class DomainEventMotherObject {

  private DomainEventMotherObject() {}

  public static final DomainEvent aDomainEvent() {

    return new DomainEvent(
        new EventId(UUID.randomUUID()),
        new EventName("SomethingHappenedInThePast"),
        new AggregateId(UUID.randomUUID()),
        new EventTimestamp(Instant.now()));
  }
}
