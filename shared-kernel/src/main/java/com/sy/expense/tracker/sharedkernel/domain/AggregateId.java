package com.sy.expense.tracker.sharedkernel.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

import java.util.UUID;
import lombok.Value;


/**
 * Value Object that represents an aggregate identity.
 *
 * @author selim
 */
@Value
public class AggregateId {

  private UUID value;

  public AggregateId(final UUID value) {

    checkArgument(nonNull(value), "Aggregate id cannot be null");
    this.value = value;
  }

}
