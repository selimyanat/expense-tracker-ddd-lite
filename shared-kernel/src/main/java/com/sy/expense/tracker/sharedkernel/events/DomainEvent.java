package com.sy.expense.tracker.sharedkernel.events;

import com.sy.expense.tracker.sharedkernel.domain.AggregateId;
import java.time.Instant;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Representation of a domain event.
 *
 * @author selim
 */
@EqualsAndHashCode(of = "id")
@Getter
public class DomainEvent {

  private final EventId id;

  private final EventName name;

  private final AggregateId aggregateId;

  private final EventTimestamp occurredOn;

  /**
   * Create a new instance of <code>{@link DomainEvent}<code/>.
   * @param id the event id
   * @param name the event name
   * @param aggregateId the aggregate id
   * @param occurredOn the event timestamp
   */
  public DomainEvent(final EventId id, final EventName name, final AggregateId aggregateId,
      final EventTimestamp occurredOn) {

    this.id = id;
    this.name = name;
    this.aggregateId = aggregateId;
    this.occurredOn = occurredOn;
  }

  /**
   * Create a new instance of <code>{@link DomainEvent}<code/> by auto generating the event id and
   * the event occurredOn.
   * @param eventName the event name
   * @param aggregateId the aggregate id
   */
  public DomainEvent(final EventName eventName, final AggregateId aggregateId) {
    this(new EventId(UUID.randomUUID()),
        eventName,
        aggregateId,
        new EventTimestamp(Instant.now()));
  }
}
