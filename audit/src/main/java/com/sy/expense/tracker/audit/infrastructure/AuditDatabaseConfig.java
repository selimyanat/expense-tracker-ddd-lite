package com.sy.expense.tracker.audit.infrastructure;

import com.sy.expense.tracker.audit.domain.AuditRepository;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class AuditDatabaseConfig {

  @Bean
  AuditRepository auditRepository(final JdbcTemplate jdbcTemplate) {

    return new JdbcAuditRepositoryImpl(jdbcTemplate);
  }

  @Bean(name = "auditDataSourceInitializer")
  DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

    var resourceDatabasePopulator = new ResourceDatabasePopulator();
    resourceDatabasePopulator.addScript(new ClassPathResource("/database/schema/audit-schema"
        + ".sql"));

    var dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
    return dataSourceInitializer;
  }

}
