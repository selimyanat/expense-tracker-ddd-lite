package com.sy.expense.tracker.identity.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import lombok.Value;

/**
 * Value Object that represents a user userName.
 */
@Value
public class Username {

  private final String value;

  public Username(final String value) {

    checkArgument(isNotEmpty(value), "User name cannot be null or empty");
    this.value = value;
  }

}
