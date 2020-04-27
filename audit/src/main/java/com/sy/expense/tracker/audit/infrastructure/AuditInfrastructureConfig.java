package com.sy.expense.tracker.audit.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    AuditDatabaseConfig.class,
    AuditSharedKernelConfig.class
})
public class AuditInfrastructureConfig {

}
