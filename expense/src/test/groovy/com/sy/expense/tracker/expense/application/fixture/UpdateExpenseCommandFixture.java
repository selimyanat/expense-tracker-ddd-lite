package com.sy.expense.tracker.expense.application.fixture;

import com.sy.expense.tracker.expense.application.command.UpdateExpenseCommand;
import com.sy.expense.tracker.expense.domain.ExpenseCategory;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import java.util.UUID;
import javax.money.Monetary;

/**
 * Return prepared instance of {@link UpdateExpenseCommand} to use during testing.
 */
public final class UpdateExpenseCommandFixture {

  private UpdateExpenseCommandFixture() {}

  public static UpdateExpenseCommand aTravelUpdateExpenseCommand() {

    return new UpdateExpenseCommand(
        new ExpenseId(UUID.randomUUID()),
        "A Taxi for the airport",
        ExpenseCategory.TRAVEL,
        Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(120).create()
    );
  }

}
