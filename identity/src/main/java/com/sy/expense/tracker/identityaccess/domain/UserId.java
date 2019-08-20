package com.sy.expense.tracker.identityaccess.domain;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;
import java.util.UUID;
import lombok.Value;

/**
 * Value Object that represents a user identity.
 */
@Value
public class UserId {

  private final UUID value;

  public UserId(final UUID value) {

    checkArgument(Objects.nonNull(value), "User id cannot be null");
    this.value = value;
  }
}
