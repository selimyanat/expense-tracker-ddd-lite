package com.sy.expense.tracker.identity.infrastructure;

import com.sy.expense.tracker.identity.domain.UserRepository;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class IdentityDatabaseConfig {

  @Bean
  UserRepository userRepository(final JdbcTemplate jdbcTemplate) {

    return new JdbcUserRepositoryImpl(jdbcTemplate);
  }

  @Bean(name = "identityDataSourceInitializer")
  DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {

    var populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("/database/schema/identity-schema"
        + ".sql"));

    var dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(populator);
    return dataSourceInitializer;
  }

}
