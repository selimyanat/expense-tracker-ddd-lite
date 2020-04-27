package com.sy.expense.tracker.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.sy.expense.tracker.identity")
@PropertySource("classpath:/identity.properties")
public class IdentityModule {

  public static void main(String[] args) {
    SpringApplication.run(IdentityModule.class, args);
  }

}
