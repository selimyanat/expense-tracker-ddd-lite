package com.sy.expense.tracker.expense.application;

import com.sy.expense.tracker.expense.application.command.AddExpenseCommand;
import com.sy.expense.tracker.expense.application.command.GetExpensesByUserIdCommand;
import com.sy.expense.tracker.expense.application.command.UpdateExpenseCommand;
import com.sy.expense.tracker.expense.domain.Expense;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import com.sy.expense.tracker.expense.domain.ExpenseRepository;
import com.sy.expense.tracker.expense.domain.UserId;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
public class ExpenseApplicationService {

  private final ExpenseRepository expenseRepository;

  private final DomainEventPublisher domainEventPublisher;

  @Transactional
  public Expense addExpense(final AddExpenseCommand aCommand) {

    var expense = Expense.addExpense(
        aCommand.getExpenseId(),
        aCommand.getDescription(),
        aCommand.getCreationDateTime(),
        aCommand.getCategory(),
        aCommand.getAmount(),
        aCommand.getUserId());

    this.expenseRepository.insertExpense(expense);
    this.publishEvents(expense.getDomainEvents());
    return expense;
  }

  @Transactional
  public void updateExpense(final UpdateExpenseCommand aCommand) {

    var existingExpense = this.existingExpense(aCommand.getExpenseId());
    existingExpense.updateExpense(aCommand.getDescription(),
        aCommand.getCategory(),
        aCommand.getAmount());

    // TODO check user permission
    this.expenseRepository.updateExpensePartially(existingExpense);
    this.publishEvents(existingExpense.getDomainEvents());
  }

  public List<Expense> getExpenses(final GetExpensesByUserIdCommand aCommand) {

    return this.expenseRepository.findByUserId(new UserId(aCommand.getUserId()));
  }

  private Expense existingExpense(final ExpenseId anExpenseId) {

    return this.expenseRepository.findByExpenseId(anExpenseId)
        .orElseThrow(
            () -> new IllegalArgumentException("Expense with given Id does not exists: " + anExpenseId));
  }


  private void publishEvents(Set<DomainEvent> theEvents) {

    this.domainEventPublisher.publish(theEvents);
  }


}
