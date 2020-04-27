package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainEventToStoredEventMapper {

  private final EventSerializer serializer;

  public StoredEvent toStoredEvent(DomainEvent aDomainEvent) {

    return StoredEvent.builder()
        .eventId(aDomainEvent.getId().getValue().toString())
        .aggregateId(aDomainEvent.getAggregateId().getValue().toString())
        .occurredOn(aDomainEvent.getOccurredOn().getValue())
        .typeName(aDomainEvent.getClass().getName())
        .payload(serializer.serialize(aDomainEvent))
        .build();
  }

}
