package com.sy.expense.tracker.audit.infrastructure;

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
public class AuditSharedKernelConfig {

  @Bean(name = "auditOutboxStore")
  OutboxStore outboxStore(final JdbcTemplate jdbcTemplate) {

    return new JdbcOutboxStoreImpl("audit_outbox_events", jdbcTemplate);
  }

  @Bean(name = "auditEventSerializer")
  EventSerializer eventSerializer() {
    return new EventSerializer();
  }

  @Bean(name = "auditMapper")
  DomainEventToStoredEventMapper mapper(
      @Qualifier("auditEventSerializer")
      EventSerializer reportEventSerializer) {

    return new DomainEventToStoredEventMapper(reportEventSerializer);
  }

  @Bean(name = "auditDomainEventPublisher")
  DomainEventPublisher domainEventPublisher(
      @Qualifier("auditOutboxStore")
      final OutboxStore outboxStore,
      @Qualifier("auditMapper")
      final DomainEventToStoredEventMapper mapper) {

    return new JustStoreDomainEventPublisher(outboxStore, mapper);
  }

  @Bean(name = "auditNotificationPublisher")
  NotificationPublisher notificationPublisher(
      final ApplicationEventPublisher commonApplicationEventPublisher,
      @Qualifier("auditOutboxStore")
      final OutboxStore outboxStore,
      @Qualifier("auditEventSerializer")
      final EventSerializer auditEventSerializer) {

    return new InMemoryNotificationPublisher(
        outboxStore,
        commonApplicationEventPublisher,
        auditEventSerializer);
  }

}
