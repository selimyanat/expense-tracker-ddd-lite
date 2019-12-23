package com.sy.expense.tracker.identity;

import com.sy.expense.tracker.identity.application.IdentityApplicationConfig;
import com.sy.expense.tracker.identity.domain.AuthenticationService;
import com.sy.expense.tracker.identity.domain.EncryptionService;
import com.sy.expense.tracker.identity.domain.UserRepository;
import com.sy.expense.tracker.identity.infrastructure.IdentityInfrastructureConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    IdentityApplicationConfig.class,
    IdentityInfrastructureConfig.class
})
public class IdentityModuleConfig {

  @Bean
  AuthenticationService authenticationService(
      final UserRepository userRepository,
      final EncryptionService encryptionService) {

    return new AuthenticationService(userRepository, encryptionService);
  }

}
