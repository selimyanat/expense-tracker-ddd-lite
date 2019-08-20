package com.sy.expense.tracker.identityaccess;

import com.sy.expense.tracker.identityaccess.application.IdentityApplicationService;
import com.sy.expense.tracker.identityaccess.application.IdentityEventProcessor;
import com.sy.expense.tracker.identityaccess.application.NotificationApplicationService;
import com.sy.expense.tracker.identityaccess.domain.AuthenticationService;
import com.sy.expense.tracker.identityaccess.domain.EncryptionService;
import com.sy.expense.tracker.identityaccess.domain.UserRepository;
import com.sy.expense.tracker.identityaccess.infrastructure.JdbcUserRepositoryImpl;
import com.sy.expense.tracker.sharedkernel.events.DomainEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class IdentityConfig {

  @Bean
  NotificationApplicationService notificationApplicationService() {
    return new NotificationApplicationService();
  }

  @Bean
  IdentityEventProcessor identityEventProcessor(NotificationApplicationService notificationApplicationService) {
    return new IdentityEventProcessor(notificationApplicationService);
  }

  @Bean
  UserRepository userRepository( JdbcTemplate jdbcTemplate) {
    return new JdbcUserRepositoryImpl(jdbcTemplate);
  }

  @Bean
  AuthenticationService authenticationService(UserRepository userRepository, EncryptionService encryptionService) {
    return new AuthenticationService(userRepository, encryptionService);
  }

  @Bean
  IdentityApplicationService identityApplicationService(UserRepository userRepository,
      EncryptionService encryptionService, DomainEventPublisher domainEventPublisher,
      AuthenticationService authenticationService) {

    return new IdentityApplicationService(userRepository,encryptionService, domainEventPublisher,
        authenticationService);
  }

}
