package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class InMemoryNotificationPublisher implements NotificationPublisher {

  private final OutboxStore store;

  private final ApplicationEventPublisher publisher;

  private final EventSerializer serializer;

  @Getter
  private long lastEventOffset;

  public InMemoryNotificationPublisher(
      final OutboxStore outboxStore,
      final ApplicationEventPublisher applicationEventPublisher,
      final EventSerializer eventSerializer) {

    this.store = outboxStore;
    this.serializer = eventSerializer;
    this.publisher = applicationEventPublisher;
    this.lastEventOffset = outboxStore.getLastStoredEventId();
  }

  @Override
  @Scheduled(fixedRate = 1000L)
  public void publishNotifications() {

    store
        .allStoredEventSince(lastEventOffset)
        .stream()
        .map(aStoredEvent -> {
          lastEventOffset = aStoredEvent.getId();
          return toDomainEvent(aStoredEvent);
        })
        .forEach(publisher::publishEvent);
  }

  @SuppressWarnings("unchecked")
  private <T extends DomainEvent> T toDomainEvent(StoredEvent aStoredEvent) {

    Class<T> domainEventClass = null;

    try {
      domainEventClass = (Class<T>) Class.forName(aStoredEvent.getTypeName());
    } catch (Exception e) {
      throw new RuntimeException("Class load error, because: " + e.getMessage());
    }

    T domainEvent = serializer.deserialize(aStoredEvent.getPayload(), domainEventClass);

    return domainEvent;
  }

}
