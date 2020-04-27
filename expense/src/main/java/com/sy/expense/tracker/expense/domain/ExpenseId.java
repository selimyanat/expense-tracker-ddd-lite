package com.sy.expense.tracker.expense.domain;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Objects;
import java.util.UUID;
import lombok.Value;

/**
 * Value Object that represents an expense id.
 */
@Value
public class ExpenseId {

  private final UUID value;

  public ExpenseId(final UUID value) {

    checkArgument(Objects.nonNull(value), "Expense id cannot be null");
    this.value = value;
  }
}
