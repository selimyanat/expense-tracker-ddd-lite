package com.sy.expense.tracker.expense.application.command;

import com.sy.expense.tracker.expense.domain.ExpenseCategory;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import javax.money.MonetaryAmount;
import lombok.Value;

/**
 * Represents a command to update an expense.
 *
 * @author selim
 */
@Value
public class UpdateExpenseCommand {

  private final ExpenseId expenseId;

  private final String description;

  private final ExpenseCategory category;

  private final MonetaryAmount amount;


}
