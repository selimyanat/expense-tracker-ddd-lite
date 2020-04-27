package com.sy.expense.tracker.identity.application;

import com.sy.expense.tracker.identity.domain.AuthenticationService;
import com.sy.expense.tracker.identity.domain.EncryptionService;
import com.sy.expense.tracker.identity.domain.UserRepository;
import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentityApplicationConfig {

  @Bean
  IdentityApplicationService identityApplicationService(
      @Qualifier("userRepository")
      final UserRepository userRepository,
      @Qualifier("identityEncryptionService")
      final EncryptionService encryptionService,
      @Qualifier("identityDomainEventPublisher")
      final DomainEventPublisher domainEventPublisher,
      @Qualifier("authenticationService")
      final AuthenticationService authenticationService) {

    return new IdentityApplicationService(
        userRepository,
        encryptionService,
        domainEventPublisher,
        authenticationService);
  }

}
