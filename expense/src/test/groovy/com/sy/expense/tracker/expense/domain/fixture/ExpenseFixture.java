package com.sy.expense.tracker.expense.domain.fixture;

import com.sy.expense.tracker.expense.domain.Expense;
import com.sy.expense.tracker.expense.domain.ExpenseCategory;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import com.sy.expense.tracker.expense.domain.UserId;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import javax.money.Monetary;

/**
 * Return prepared instance of {@link Expense} to use during testing.
 */
public final class ExpenseFixture {

  private ExpenseFixture() {}

  public static final Expense aHotelExpense() {

    return new Expense(
        new ExpenseId(UUID.fromString("ee994fe9-c339-5586-b002-9996552aec6c")),
        "Hotel in Barcelona",
        LocalDateTime.ofInstant(Instant.ofEpochSecond(1686637660l), ZoneId.systemDefault()),
        ExpenseCategory.HOTEL,
        Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(120).create(),
        new UserId("ee654fe9-c349-4370-a472-8096552aec5f")
    );
  }



}
