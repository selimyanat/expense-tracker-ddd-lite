package com.sy.expense.tracker.expense.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository backing {@link Expense}.
 *
 * @author selim
 */
public interface ExpenseRepository {

  void insertExpense(Expense expense);

  void updateExpensePartially(Expense expense);

  Optional<Expense> findByExpenseId(ExpenseId expenseId);

  List<Expense> findByUserId(UserId userId);

}
