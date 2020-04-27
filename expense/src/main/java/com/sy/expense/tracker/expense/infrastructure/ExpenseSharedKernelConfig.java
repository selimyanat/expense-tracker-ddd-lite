package com.sy.expense.tracker.expense.infrastructure;

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
public class ExpenseSharedKernelConfig {

  @Bean(name = "expenseOutboxStore")
  OutboxStore outboxStore(final JdbcTemplate jdbcTemplate) {

    return new JdbcOutboxStoreImpl("expense_outbox_events", jdbcTemplate);
  }

  @Bean(name = "expenseEventSerializer")
  EventSerializer eventSerializer() {
    return new EventSerializer();
  }

  @Bean(name = "expenseMapper")
  DomainEventToStoredEventMapper mapper(
      @Qualifier("expenseEventSerializer")
      EventSerializer reportEventSerializer) {

    return new DomainEventToStoredEventMapper(reportEventSerializer);
  }

  @Bean(name = "expenseDomainEventPublisher")
  DomainEventPublisher domainEventPublisher(
      @Qualifier("expenseOutboxStore")
      final OutboxStore outboxStore,
      @Qualifier("expenseMapper")
      final DomainEventToStoredEventMapper mapper) {

    return new JustStoreDomainEventPublisher(outboxStore, mapper);
  }

  @Bean(name = "expenseNotificationPublisher")
  NotificationPublisher notificationPublisher(
      final ApplicationEventPublisher commonApplicationEventPublisher,
      @Qualifier("expenseOutboxStore")
      final OutboxStore outboxStore,
      @Qualifier("expenseEventSerializer")
      final EventSerializer expenseEventSerializer) {

    return new InMemoryNotificationPublisher(
        outboxStore,
        commonApplicationEventPublisher,
        expenseEventSerializer);
  }

}
