package com.sy.expense.tracker.sharedkernel.events;

import java.util.Set;

public interface DomainEventPublisher {

  void publish(Set<DomainEvent> domainEvents);
}
