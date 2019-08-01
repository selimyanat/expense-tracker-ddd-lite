package com.sy.expense.tracker.sharedkernel;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

import java.time.Instant;
import lombok.Value;

/**
 * Value Object that represents an aggregate identity.
 *
 * @author selim
 */
@Value
public class EventTimestamp {

  private Instant value;

  EventTimestamp(final Instant value) {

    checkArgument(nonNull(value), "Timestamp cannot be null");
    this.value = value;
  }

}
