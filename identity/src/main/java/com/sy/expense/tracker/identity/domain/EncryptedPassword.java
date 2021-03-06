package com.sy.expense.tracker.identity.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import lombok.Value;

/**
 * Value Object that represents an encrypted password.
 */
@Value
public class EncryptedPassword {

  private final String value;

  public EncryptedPassword(final String value) {

    checkArgument(isNotEmpty(value), "User password cannot be null or empty");
    this.value = value;
  }

}
