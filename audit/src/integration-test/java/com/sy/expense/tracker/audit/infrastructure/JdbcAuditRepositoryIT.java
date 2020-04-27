package com.sy.expense.tracker.audit.infrastructure;

import static com.sy.expense.tracker.audit.fixture.AuditFixture.anAuditRecordToCreate;
import static com.sy.expense.tracker.audit.fixture.AuditFixture.firstDatabaseAuditRecord;
import static com.sy.expense.tracker.audit.fixture.AuditFixture.secondDatabaseAuditRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Integration test for {@link JdbcAuditRepositoryImpl}
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@JdbcTest
@Sql(
    statements = {"insert into audit ("
        + "event_uuid, "
        + "event_name) "
        + "values ("
        + "'cc654fe9-c239-4272-a002-8096552aec5f', "
        + "'EXPENSE_ADDED'"
        + ") ",
        "insert into audit ("
            + "event_uuid, "
            + "event_name) "
            + "values ("
            + "'dd654fe9-c399-5383-a008-9097663aec5f', "
            + "'USER_REGISTERED'"
            + ") "
    })
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:/audit.properties")
public class JdbcAuditRepositoryIT {

  @Autowired
  @Qualifier("auditRepository")
  private JdbcAuditRepositoryImpl underTest;

  @Test
  public void findAll_returns_allAuditRecords() {

    assertThat(underTest.findAll())
        .hasSize(2)
        .contains(firstDatabaseAuditRecord(), secondDatabaseAuditRecord());
  }

  @Test
  public void insertAudit_isOk() {

    var anAuditRecordToCreate = anAuditRecordToCreate();

    underTest.insertAudit(anAuditRecordToCreate);
    assertThat(underTest.findAll())
        .hasSize(3)
        .anyMatch(audit ->
            audit.getEventName().equals(audit.getEventName()) &&
                audit.getId().equals(audit.getId())
        );
  }



  @Configuration
  @Import({AuditDatabaseConfig.class, JdbcAuditRepositoryImpl.class})
  static class TestContextConfiguration {

  }

}
