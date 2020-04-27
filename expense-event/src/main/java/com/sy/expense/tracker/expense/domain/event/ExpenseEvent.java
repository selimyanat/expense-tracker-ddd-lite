package com.sy.expense.tracker.expense.domain.event;

import com.sy.expense.tracker.sharedkernel.domain.AggregateId;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import com.sy.expense.tracker.sharedkernel.domain.EventName;
import java.util.UUID;
import lombok.Value;

/**
 * Holds all expense related events.
 *
 * @author selim
 */
public interface ExpenseEvent {

  /**
   * Event representing an expense creation.
   */
  @Value
  class ExpenseAdded extends DomainEvent {

    static final String EXPENSE_ADDED = "expenseAdded";

    private final String userId;

    private final String category;

    private final double amount;

    private final String currency;

    public ExpenseAdded(
        final UUID expenseId,
        final String aUserId,
        final String aCategory,
        final double anAmount,
        final String aCurrency) {

      super(new EventName(EXPENSE_ADDED), new AggregateId(expenseId));

      this.userId = aUserId;
      this.category = aCategory;
      this.amount = anAmount;
      this.currency = aCurrency;
    }
  }

  @Value
  class ExpenseUpdated extends DomainEvent {

    static final String EXPENSE_UPDATED = "expenseUpdated";

    private final String userId;

    private final String category;

    private final double amount;

    private final String currency;

    public ExpenseUpdated(
        final UUID expenseId,
        final String aUserId,
        final String aCategory,
        final double anAmount,
        final String aCurrency) {

      super(new EventName(EXPENSE_UPDATED), new AggregateId(expenseId));

      this.userId = aUserId;
      this.category = aCategory;
      this.amount = anAmount;
      this.currency = aCurrency;
    }

  }

}
