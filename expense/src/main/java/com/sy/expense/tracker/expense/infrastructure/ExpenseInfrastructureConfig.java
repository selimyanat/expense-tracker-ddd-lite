package com.sy.expense.tracker.expense.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    ExpenseDatabaseConfig.class,
    ExpenseSharedKernelConfig.class
})
public class ExpenseInfrastructureConfig {

}
