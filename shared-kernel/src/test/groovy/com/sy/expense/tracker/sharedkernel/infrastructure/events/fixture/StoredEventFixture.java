package com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture;

import static com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture.DomainEventFixture.aDummyDomainEvent;

import com.google.gson.Gson;
import com.sy.expense.tracker.sharedkernel.domain.ADummyDomainEvent;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.StoredEvent;
import lombok.SneakyThrows;

/**
 * Return prepared instance of {@link StoredEvent} to use during testing.
 */
public final class StoredEventFixture {

  private static final Gson SERIALIZER = new Gson().newBuilder().create();

  private StoredEventFixture() {
  }

  @SneakyThrows
  public static StoredEvent aStoredDummyEvent(long id) {

    var aDummyDomainEvent = aDummyDomainEvent();

    return StoredEvent
        .builder()
        .id(id)
        .eventId(aDummyDomainEvent.getId().getValue().toString())
        .occurredOn(aDummyDomainEvent.getOccurredOn().getValue())
        .aggregateId(aDummyDomainEvent.getAggregateId().getValue().toString())
        .typeName(ADummyDomainEvent.class.getName())
        .payload(SERIALIZER.toJson(aDummyDomainEvent))
        .build();
  }

}
