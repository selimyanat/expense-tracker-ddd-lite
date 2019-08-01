package com.sy.expense.tracker.sharedkernel;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

import java.util.UUID;
import lombok.Value;

/**
 * Value Object that represents an event identity.
 *
 * @author selim
 */
@Value
public class EventId {

  private UUID value;

  EventId(final UUID value) {

    checkArgument(nonNull(value), "Event id cannot be null");
    this.value = value;
  }

}
