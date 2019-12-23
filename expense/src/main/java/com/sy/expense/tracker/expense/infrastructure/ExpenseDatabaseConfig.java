package com.sy.expense.tracker.expense.infrastructure;

import com.sy.expense.tracker.expense.domain.ExpenseRepository;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class ExpenseDatabaseConfig {

  @Bean
  ExpenseRepository expenseRepository(final JdbcTemplate jdbcTemplate) {

    return new JdbcExpenseRepositoryImpl(jdbcTemplate);
  }

  @Bean(name = "expenseDataSourceInitializer")
  DataSourceInitializer dataSourceInitializer(DataSource dataSource) {

    var resourceDatabasePopulator = new ResourceDatabasePopulator();
    resourceDatabasePopulator.addScript(new ClassPathResource("/database/schema/expense-schema"
        + ".sql"));

    var dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
    return dataSourceInitializer;
  }

}
