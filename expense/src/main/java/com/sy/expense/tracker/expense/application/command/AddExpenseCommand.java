package com.sy.expense.tracker.expense.application.command;

import com.sy.expense.tracker.expense.domain.ExpenseCategory;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import com.sy.expense.tracker.expense.domain.UserId;
import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import lombok.Value;

/**
 * Represents a command to register an expense.
 *
 * @author selim
 */
@Value
public class AddExpenseCommand {

  private final ExpenseId expenseId;

  private final String description;

  private final LocalDateTime creationDateTime;

  private final UserId userId;

  private final ExpenseCategory category;

  private final MonetaryAmount amount;

}
