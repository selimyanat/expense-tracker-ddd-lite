package com.sy.expense.tracker.sharedkernel.events.internal;

import com.sy.expense.tracker.sharedkernel.events.DomainEventPublisher;
import com.sy.expense.tracker.sharedkernel.events.DomainEvent;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JustForwardDomainEventPublisher implements DomainEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publish(Set<DomainEvent> domainEvents) {

    domainEvents.forEach(domainEvent -> applicationEventPublisher.publishEvent(domainEvent));
  }
}
