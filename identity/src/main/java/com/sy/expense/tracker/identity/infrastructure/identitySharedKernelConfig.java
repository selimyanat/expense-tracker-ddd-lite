package com.sy.expense.tracker.identity.infrastructure;

import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.DomainEventToStoredEventMapper;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.EventSerializer;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.InMemoryNotificationPublisher;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.JdbcOutboxStoreImpl;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.JustStoreDomainEventPublisher;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.NotificationPublisher;
import com.sy.expense.tracker.sharedkernel.infrastructure.events.OutboxStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class identitySharedKernelConfig {

  @Bean(name = "identityOutboxStore")
  OutboxStore outboxStore(final JdbcTemplate jdbcTemplate) {

    return new JdbcOutboxStoreImpl("identity_outbox_events", jdbcTemplate);
  }

  @Bean(name = "identityEventSerializer")
  EventSerializer identityEventSerializer() {
    return new EventSerializer();
  }

  @Bean(name = "identityMapper")
  DomainEventToStoredEventMapper mapper(
      @Qualifier("identityEventSerializer")
      final EventSerializer eventSerializer) {

    return new DomainEventToStoredEventMapper(eventSerializer);
  }

  @Bean(name = "identityDomainEventPublisher")
    DomainEventPublisher identityDomainEventPublisher(
      @Qualifier("identityOutboxStore")
      final OutboxStore outboxStore,
      @Qualifier("identityMapper")
      final DomainEventToStoredEventMapper mapper) {

    return new JustStoreDomainEventPublisher(outboxStore, mapper);
  }

  @Bean(name = "identityNotificationPublisher")
  NotificationPublisher identityNotificationPublisher(
      final ApplicationEventPublisher commonApplicationEventPublisher,
      @Qualifier("identityOutboxStore")
      final OutboxStore outboxStore,
      @Qualifier("identityEventSerializer")
      final EventSerializer eventSerializer) {

    return new InMemoryNotificationPublisher(
        outboxStore,
        commonApplicationEventPublisher,
        eventSerializer);
  }

}
