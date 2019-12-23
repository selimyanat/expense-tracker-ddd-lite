package com.sy.expense.tracker.audit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.sy.expense.tracker.audit")
@PropertySource("classpath:/audit.properties")
public class AuditModule {

}
