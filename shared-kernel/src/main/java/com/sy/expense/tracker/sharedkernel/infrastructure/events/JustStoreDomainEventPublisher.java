package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JustStoreDomainEventPublisher implements DomainEventPublisher {

  private final OutboxStore store;

  private final DomainEventToStoredEventMapper mapper;

  @Override
  public void publish(Set<DomainEvent> domainEvents) {
    domainEvents.forEach(aDomainEvent -> this.store.append(toStoredEvent(aDomainEvent)));
  }

  private StoredEvent toStoredEvent(DomainEvent aDomainEvent) {
    return mapper.toStoredEvent(aDomainEvent);
  }

}
