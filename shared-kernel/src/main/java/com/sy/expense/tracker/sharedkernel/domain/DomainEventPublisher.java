package com.sy.expense.tracker.sharedkernel.domain;

import java.util.Set;

public interface DomainEventPublisher {

  void publish(Set<DomainEvent> domainEvents);
}
