package com.sy.expense.tracker.audit.domain;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;
import java.util.UUID;
import lombok.Value;

/**
 * Value Object that represents an audit id.
 */
@Value
public class AuditId {

  private final UUID value;

  public AuditId(final UUID value) {

    checkArgument(Objects.nonNull(value), "Audit id cannot be null");
    this.value = value;
  }

}
