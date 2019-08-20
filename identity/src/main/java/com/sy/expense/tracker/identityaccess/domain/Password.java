package com.sy.expense.tracker.identityaccess.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import lombok.Value;

/**
 * Value Object that represents a password hash.
 */
@Value
public class Password {

  private final String value;

  public Password(final String value) {
    // TODO enhance with validation rule: http://www.passay.org/reference/
    // TODO use a factory instead
    checkArgument(isNotEmpty(value), "User password cannot be null or empty");
    this.value = value;
  }
}
