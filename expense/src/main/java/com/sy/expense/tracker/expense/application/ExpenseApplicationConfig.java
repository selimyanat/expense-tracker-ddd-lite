package com.sy.expense.tracker.expense.application;

import com.sy.expense.tracker.expense.domain.ExpenseRepository;
import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseApplicationConfig {

  @Bean
  ExpenseApplicationService expenseApplicationService(
      @Qualifier("expenseRepository")
      final ExpenseRepository expenseRepository,
      @Qualifier("expenseDomainEventPublisher")
      final DomainEventPublisher domainEventPublisher) {

    return new ExpenseApplicationService(expenseRepository, domainEventPublisher);
  }

}
