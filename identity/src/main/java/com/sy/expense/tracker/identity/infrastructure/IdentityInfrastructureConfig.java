package com.sy.expense.tracker.identity.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Import({
    IdentityDatabaseConfig.class,
    identitySharedKernelConfig.class
})
public class IdentityInfrastructureConfig {

  @Bean(name = "identityPasswordEncoder")
  BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean(name = "identityEncryptionService")
  BCryptEncryptionService bCryptEncryptionService(final BCryptPasswordEncoder passwordEncoder) {

    return new BCryptEncryptionService(passwordEncoder);
  }


}
