package com.sy.expense.tracker.sharedkernel;

import java.util.Set;

public interface DomainEventPublisher {

  void publish(Set<DomainEvent> domainEvents);
}
