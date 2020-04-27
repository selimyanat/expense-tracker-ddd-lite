package com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture;

import com.sy.expense.tracker.sharedkernel.domain.ADummyDomainEvent;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import java.time.Instant;
import java.util.UUID;

/**
 * Return prepared instance of {@link DomainEvent} to use during testing.
 */
public final class DomainEventFixture {

  private DomainEventFixture() {
  }

  public static final ADummyDomainEvent aDummyDomainEvent() {

    return new ADummyDomainEvent(
        UUID.fromString("3f0bef4e-823d-432a-9579-7fdcbaf6d98b"),
        UUID.fromString("e46d635e-b9af-40a0-b5b7-7ffc579f1137"),
        Instant.ofEpochSecond(1577469353l),
        "SomethingHappenedInThePast");
  }
}
