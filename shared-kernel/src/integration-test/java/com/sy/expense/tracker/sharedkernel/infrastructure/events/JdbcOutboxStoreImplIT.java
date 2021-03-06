package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import static com.sy.expense.tracker.sharedkernel.fixture.MyEventStoredFixture.aNewStoredEvent;
import static com.sy.expense.tracker.sharedkernel.fixture.MyEventStoredFixture.aStoredEvent;
import static com.sy.expense.tracker.sharedkernel.fixture.MyEventStoredFixture.anotherStoredEvent;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@JdbcTest
@Sql(
    statements = {
        "CREATE TABLE IF NOT EXISTS my_outbox_store (\n"
            + "    stored_event_id  INTEGER NOT NULL  GENERATED BY DEFAULT AS IDENTITY,\n"
            + "    event_id VARCHAR(36) NOT NULL,\n"
            + "    event_aggregate_id VARCHAR(128) NOT NULL,\n"
            + "    event_type VARCHAR(255) NOT NULL,\n"
            + "    event_timestamp BIGINT NOT NULL,\n"
            + "    event_payload TEXT NOT NULL,\n"
            + "    PRIMARY KEY (stored_event_id)\n"
            + ");",
        "INSERT INTO MY_OUTBOX_STORE (\n"
            + "  STORED_EVENT_ID,\n"
            + "  EVENT_ID,\n"
            + "  event_aggregate_id,\n"
            + "  event_type,\n"
            + "  event_timestamp,\n"
            + "  event_payload) VALUES\n"
            + "( 1,\n"
            + "  '3f0bef4e-823d-432a-9579-7fdcbaf6d98b',\n"
            + "  'e46d635e-b9af-40a0-b5b7-7ffc579f1137',\n"
            + "  'this represents a class name appended to the package name',\n"
            + "  1577469353,\n"
            + "  '{message: this is a json data structure}'\n"
            + ");",
        "INSERT INTO my_outbox_store (\n"
            + "  stored_event_id,\n"
            + "  event_id,\n"
            + "  event_aggregate_id,\n"
            + "  event_type,\n"
            + "  event_timestamp,\n"
            + "  event_payload) VALUES\n"
            + "( 2,\n"
            + "  '6f0bef4e-328d-234a-9579-8fdcbaf7d99b',\n"
            + "  'e56d746e-b9af-50a0-b6b8-8ffc679f2248',\n"
            + "  'this represents a class name appended to the package name',\n"
            + "  1677469353,\n"
            + "  '{message: this is a json data structure}'\n"
            + ");"
    })
public class JdbcOutboxStoreImplIT {

  @Autowired
  private JdbcOutboxStoreImpl jdbcOutboxStore;

  @Test
  public void getLastStoredEventId_returnsLatestId() {

    assertThat(jdbcOutboxStore.getLastStoredEventId()).isEqualTo(2);
  }

  @Test
  public void allStoredEventSince_theBeginning_returnsAllEvents() {

    assertThat(jdbcOutboxStore.allStoredEventSince(0))
        .contains(aStoredEvent(), anotherStoredEvent()
        );
  }

  @Test
  public void allStoredEventSince_theFirstEvent_returnsTheSecondEvent() {

    assertThat(jdbcOutboxStore.allStoredEventSince(1))
        .contains(anotherStoredEvent());
  }

  @Test
  public void append_insertNewEvent_isOk() {

    var aNewStoredEvent = aNewStoredEvent();
    jdbcOutboxStore.append(aNewStoredEvent());

    var expectedEvents = jdbcOutboxStore.allStoredEventSince(2);
    assertThat(expectedEvents.size()).isEqualTo(1);
    var lastEvent = expectedEvents.get(0);
    assertThat(lastEvent.getId()).isEqualTo(3);
    assertThat(lastEvent.getEventId()).isEqualTo(aNewStoredEvent.getEventId());
    assertThat(lastEvent.getAggregateId()).isEqualTo(aNewStoredEvent.getAggregateId());
    assertThat(lastEvent.getOccurredOn()).isEqualTo(aNewStoredEvent.getOccurredOn());
    assertThat(lastEvent.getTypeName()).isEqualTo(aNewStoredEvent.getTypeName());
    assertThat(lastEvent.getPayload()).isEqualTo(aNewStoredEvent.getPayload());

  }


  @Configuration
  static class TestContextConfiguration {

    @Bean
    JdbcOutboxStoreImpl jdbcEventStoreImpl(JdbcTemplate jdbcTemplate) {
      return new JdbcOutboxStoreImpl("MY_OUTBOX_STORE", jdbcTemplate);
    }
  }

}
