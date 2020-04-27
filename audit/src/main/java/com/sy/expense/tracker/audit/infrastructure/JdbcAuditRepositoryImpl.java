package com.sy.expense.tracker.audit.infrastructure;

import com.sy.expense.tracker.audit.domain.Audit;
import com.sy.expense.tracker.audit.domain.AuditId;
import com.sy.expense.tracker.audit.domain.AuditRepository;
import io.vavr.control.Try;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Slf4j
public class JdbcAuditRepositoryImpl implements AuditRepository {

  private final JdbcTemplate jdbcTemplate;

  private final AuditRowMapper expenseRowMapper;

  private SimpleJdbcInsert simpleJdbcInsert;

  /**
   * Create a new instance of <code>{@link JdbcAuditRepositoryImpl}</code>
   *
   * @param auditJdbcTemplate the jdbc template to interact with the database
   */
  public JdbcAuditRepositoryImpl(JdbcTemplate auditJdbcTemplate) {

    this.jdbcTemplate = auditJdbcTemplate;
    this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
    this.simpleJdbcInsert
        .withTableName("audit")
        .usingGeneratedKeyColumns("audit_id");
    this.expenseRowMapper = new AuditRowMapper();
  }

  @Override
  public List<Audit> findAll() {

    var result = Try.of(() -> this.jdbcTemplate
        .query("select * from audit order by audit_id desc limit 20", expenseRowMapper))
        .onFailure(throwable -> LOG.warn("Fail to read audit records from database", throwable));

    return result.getOrElse(Collections.emptyList());
  }

  @Override
  public void insertAudit(Audit audit) {

    final Map<String, Object> parameters = new HashMap<>(2);
    parameters.put("event_uuid", audit.getId().getValue());
    parameters.put("event_name", audit.getEventName());

    Try.of(() -> this.simpleJdbcInsert.executeAndReturnKey(parameters))
        .onFailure(throwable -> LOG.warn("Fail to create audit with id {} ",
            audit.getId().getValue(), throwable))
        .onSuccess(id -> LOG.debug( "Newly audit created in database with ID {} and UUID {}",
            id, audit.getId().getValue()));
  }

  private class AuditRowMapper implements RowMapper<Audit>  {

    @Override
    public Audit mapRow(ResultSet rs, int rowNum) throws SQLException {

      var audit = new Audit(
          new AuditId(UUID.fromString(rs.getString("event_uuid"))),
          rs.getString("event_name"));
      return audit;
    }
  }
}
