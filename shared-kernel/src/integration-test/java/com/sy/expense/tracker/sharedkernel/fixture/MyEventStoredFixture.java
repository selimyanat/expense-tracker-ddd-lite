package com.sy.expense.tracker.sharedkernel.fixture;

import com.sy.expense.tracker.sharedkernel.infrastructure.events.StoredEvent;
import java.time.Instant;
import lombok.SneakyThrows;

/**
 * Return prepared instance of {@link StoredEvent} to use during testing.
 */
public final class MyEventStoredFixture {

  private MyEventStoredFixture() {
  }

  @SneakyThrows
  public static StoredEvent aStoredEvent() {

    return StoredEvent
        .builder()
        .id(1)
        .eventId("3f0bef4e-823d-432a-9579-7fdcbaf6d98b")
        .aggregateId("e46d635e-b9af-40a0-b5b7-7ffc579f1137")
        .occurredOn(Instant.ofEpochSecond(1577469353l))
        .typeName("this represents a class name appended to the package name")
        .payload("{message: this is a json data structure}")
        .build();
  }

  @SneakyThrows
  public static StoredEvent anotherStoredEvent() {

    return StoredEvent
        .builder()
        .id(2)
        .eventId("6f0bef4e-328d-234a-9579-8fdcbaf7d99b")
        .aggregateId("e56d746e-b9af-50a0-b6b8-8ffc679f2248")
        .occurredOn(Instant.ofEpochSecond(1677469353l))
        .typeName("this represents a class name appended to the package name")
        .payload("{message: this is a json data structure}")
        .build();
  }

  @SneakyThrows
  public static StoredEvent aNewStoredEvent() {

    return StoredEvent
        .builder()
        .eventId("7f0bef5e-38d-344a-9689-8fdcbaf7d99b")
        .aggregateId("e88857e-b9af-60a0-b7b9-9ffc989f5549")
        .occurredOn(Instant.ofEpochSecond(1688479455l))
        .typeName("this represents a class name appended to the package name")
        .payload("{message: this is a json data structure}")
        .build();
  }
}
