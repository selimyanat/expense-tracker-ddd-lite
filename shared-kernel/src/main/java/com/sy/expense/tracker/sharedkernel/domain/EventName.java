package com.sy.expense.tracker.sharedkernel.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import lombok.Value;

/**
 * Value Object that represents an aggregate identity.
 *
 * @author selim
 */
@Value
public class EventName {

  private final String value;

  public EventName(String value) {

    checkArgument(isNotEmpty(value), "Event name cannot be null or empty");
    this.value = value;
  }
}
