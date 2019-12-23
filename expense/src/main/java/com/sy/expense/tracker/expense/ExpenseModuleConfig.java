package com.sy.expense.tracker.expense;

import com.sy.expense.tracker.expense.application.ExpenseApplicationConfig;
import com.sy.expense.tracker.expense.infrastructure.ExpenseInfrastructureConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    ExpenseInfrastructureConfig.class,
    ExpenseApplicationConfig.class
})
public class ExpenseModuleConfig {


}
