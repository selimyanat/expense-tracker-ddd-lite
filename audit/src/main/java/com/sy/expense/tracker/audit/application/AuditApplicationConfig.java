package com.sy.expense.tracker.audit.application;

import com.sy.expense.tracker.audit.domain.AuditRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditApplicationConfig {

  @Bean
  AuditApplicationService auditApplicationService(AuditRepository auditRepository) {
    return new AuditApplicationService(auditRepository);
  }

  @Bean
  AuditEventProcessor auditEventProcessor(final AuditRepository auditRepository) {
    return new AuditEventProcessor(auditRepository);
  }


}
