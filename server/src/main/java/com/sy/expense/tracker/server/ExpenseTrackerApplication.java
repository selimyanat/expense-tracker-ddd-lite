package com.sy.expense.tracker.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.sy.expense.tracker", exclude = { SecurityAutoConfiguration.class })
public class ExpenseTrackerApplication  {

  public static void main(String[] args) {

    SpringApplication.run(ExpenseTrackerApplication.class, args);
  }

}
