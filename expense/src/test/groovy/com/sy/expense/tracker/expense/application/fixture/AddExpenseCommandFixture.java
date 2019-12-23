package com.sy.expense.tracker.expense.application.fixture;

import com.sy.expense.tracker.expense.application.command.AddExpenseCommand;
import com.sy.expense.tracker.expense.domain.ExpenseCategory;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import com.sy.expense.tracker.expense.domain.UserId;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.money.Monetary;

/**
 * Return prepared instance of {@link AddExpenseCommand} to use during testing.
 */
public final class AddExpenseCommandFixture {

  private AddExpenseCommandFixture() {}

  public static AddExpenseCommand aTaxiExpenseCommand() {

    return new AddExpenseCommand(
        new ExpenseId(UUID.randomUUID()),
        "A Taxi for the airport",
        LocalDateTime.now(),
        new UserId(UUID.randomUUID().toString()),
        ExpenseCategory.TAXI,
        Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(120).create()
    );
  }
}
