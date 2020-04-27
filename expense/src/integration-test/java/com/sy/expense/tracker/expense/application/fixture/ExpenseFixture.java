package com.sy.expense.tracker.expense.application.fixture;

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

  public static final Expense firstDatabaseExpense() {

    return new Expense(
        new ExpenseId(UUID.fromString("cc654fe9-c239-4272-a002-8096552aec5f")),
        "Return from airport",
        LocalDateTime.ofInstant(Instant.ofEpochSecond(1586637660l), ZoneId.systemDefault()),
        ExpenseCategory.TAXI,
        Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(50.50).create(),
        new UserId("user1@somewhere.com")
    );
  }

  public static final Expense secondDatabaseExpense() {

    return new Expense(
        new ExpenseId(UUID.fromString("cc994fe9-c339-5586-b002-9996552aec6c")),
        "An opera",
        LocalDateTime.ofInstant(Instant.ofEpochSecond(1586637660l), ZoneId.systemDefault()),
        ExpenseCategory.ENTERTAINMENT,
        Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(150.50).create(),
        new UserId("user1@somewhere.com")
    );
  }

  public static final Expense anExpenseToCreate() {

    return new Expense(
        new ExpenseId(UUID.fromString("ee994fe9-c339-5586-b002-9996552aec6c")),
        "Hotel in Barcelona",
        LocalDateTime.ofInstant(Instant.ofEpochSecond(1686637660l), ZoneId.systemDefault()),
        ExpenseCategory.HOTEL,
        Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(120).create(),
        new UserId("user1@somewhere.com")
    );
  }

  public static final Expense anExpenseToUpdate() {

    return new Expense(
        new ExpenseId(UUID.fromString("cc654fe9-c239-4272-a002-8096552aec5f")),
        "a business travel in barcelona",
        LocalDateTime.ofInstant(Instant.ofEpochSecond(1586637660l), ZoneId.systemDefault()),
        ExpenseCategory.TRAVEL,
        Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(1500).create(),
        new UserId("user1@somewhere.com")
    );
  }

}
