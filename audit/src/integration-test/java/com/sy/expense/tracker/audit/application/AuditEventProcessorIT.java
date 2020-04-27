package com.sy.expense.tracker.audit.application;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

import com.sy.expense.tracker.audit.domain.Audit;
import com.sy.expense.tracker.audit.domain.AuditRepository;
import com.sy.expense.tracker.expense.domain.event.ExpenseEvent.ExpenseAdded;
import com.sy.expense.tracker.identity.domain.event.UserEvent.UserRegistered;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Integration test for class {@link AuditEventProcessor}
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AuditEventProcessorIT {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @MockBean
  private AuditRepository auditRepository;

  @Autowired
  private AuditEventProcessor underTest;

  @Test
  public void onNewUserRegistered_a_new_auditRecord_is_created() {

    applicationEventPublisher.publishEvent(new UserRegistered(
        UUID.randomUUID(),
        "user1",
        "user1@somewhere.com"));

    verify(auditRepository).insertAudit(any(Audit.class));
  }

  @Test
  public void onExpenseAdded_a_new_auditRecord_is_created() {

    applicationEventPublisher.publishEvent(new ExpenseAdded(
        UUID.randomUUID(),
        "user1@somewhere.com",
        "TRAVEL",
        120,
        "USD"));

    verify(auditRepository).insertAudit(any(Audit.class));
  }



  @Configuration
  static class TestContextConfiguration {

    @Bean
    AuditEventProcessor auditEventProcessor(AuditRepository auditRepository) {
      return new AuditEventProcessor(auditRepository);
    }
  }

}
