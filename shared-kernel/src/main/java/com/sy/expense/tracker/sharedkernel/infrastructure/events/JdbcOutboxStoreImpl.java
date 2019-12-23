package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import io.vavr.control.Try;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Slf4j
public class JdbcOutboxStoreImpl implements OutboxStore {

  @Getter
  private final String tableName;

  private final JdbcTemplate jdbcTemplate;

  private final StoredEventRowMapper rowMapper;

  private SimpleJdbcInsert simpleJdbcInsert;


  /**
   * Create a new instance of <code>{@link JdbcOutboxStoreImpl}</code>
   *
   * @param jdbcTemplate the jdbc template to interact with the database
   */
  public JdbcOutboxStoreImpl(final String tableName, final JdbcTemplate jdbcTemplate) {

    this.tableName = tableName;
    this.jdbcTemplate = jdbcTemplate;
    this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
    this.simpleJdbcInsert
        .withTableName(this.tableName)
        .usingGeneratedKeyColumns("stored_event_id");
    this.rowMapper = new StoredEventRowMapper();
  }


  @Override
  public void append(StoredEvent aStoredEvent) {

    final Map<String, Object> parameters = new HashMap<>(6);
    parameters.put("event_id", aStoredEvent.getEventId());
    parameters.put("event_aggregate_id", aStoredEvent.getAggregateId());
    parameters.put("event_timestamp", aStoredEvent.getOccurredOn().getEpochSecond());
    parameters.put("event_type", aStoredEvent.getTypeName());
    parameters.put("event_payload", aStoredEvent.getPayload());

    var id = this.simpleJdbcInsert.executeAndReturnKey(parameters);
    LOG.debug("Newly stored event {} created in database with ID {} for aggregate type",
        id, aStoredEvent.getTypeName());
  }


  @Override
  public List<StoredEvent> allStoredEventSince(long aStoredEventId) {

    var response = Try.of(() -> this.jdbcTemplate
        .query("select * from " + tableName + " where stored_event_id  > ?",
            rowMapper,
            aStoredEventId))
        .onFailure(throwable -> LOG.error("Fail to read events from database", throwable));
    return response.getOrElse(Collections.emptyList());
  }

  @Override
  public long getLastStoredEventId() {

    var response = Try.of(() -> this.jdbcTemplate
        .queryForObject("select coalesce(max(stored_event_id), 0) from " + tableName, Long.class))
        .onFailure(throwable -> LOG.error("Fail to read events from outbox", throwable));

    return response.getOrElse(() -> 0l);
  }

  private static class StoredEventRowMapper implements RowMapper<StoredEvent> {

    @Override
    public StoredEvent mapRow(ResultSet rs, int rowNum) throws SQLException {

      var aStoredEvent = new StoredEvent(
          rs.getLong("stored_event_id"),
          rs.getString("event_id"),
          rs.getString("event_aggregate_id"),
          Instant.ofEpochSecond(rs.getLong("event_timestamp")),
          rs.getString("event_type"),
          rs.getString("event_payload")
      );

      return aStoredEvent;
    }
  }
}
