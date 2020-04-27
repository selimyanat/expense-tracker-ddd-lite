package com.sy.expense.tracker.audit;

import com.sy.expense.tracker.audit.application.AuditApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({
    AuditApplicationConfig.class
})
public class AuditModuleConfig {

}
