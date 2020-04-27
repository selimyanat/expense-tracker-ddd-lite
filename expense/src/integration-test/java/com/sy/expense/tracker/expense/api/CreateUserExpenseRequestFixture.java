package com.sy.expense.tracker.expense.api;

import com.sy.expense.tracker.expense.domain.ExpenseCategory;

/**
 * Return prepared instance of {@link CreateUserExpenseRequest} to use during testing.
 */
public class CreateUserExpenseRequestFixture {

  private CreateUserExpenseRequestFixture() {}

  public static final CreateUserExpenseRequest createUser1ExpenseRequest() {

    return new CreateUserExpenseRequest()
        .setEmail("user1@somewhere.com")
        .setDescription("A train ticket")
        .setCategory(ExpenseCategory.TRAVEL)
        .setAmount(50);
  }

}
