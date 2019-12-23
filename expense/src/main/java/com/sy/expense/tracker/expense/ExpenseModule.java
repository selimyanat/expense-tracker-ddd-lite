package com.sy.expense.tracker.expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.sy.expense.tracker.expense")
@PropertySource("classpath:/expense.properties")
public class ExpenseModule {

  public static void main(String[] args) {
    SpringApplication.run(ExpenseModule.class, args);
  }

}
