package com.sy.expense.tracker.expense.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import com.sy.expense.tracker.expense.domain.event.ExpenseEvent;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.money.MonetaryAmount;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents an expense with its core attributes.
 *
 * @author selim
 */
@EqualsAndHashCode(of = "id")
@Getter
public class Expense {

  private final ExpenseId id;

  private final UserId userId;

  private String description;

  private final LocalDateTime creationDateTime;

  private ExpenseCategory category;

  private MonetaryAmount amount;

  private final Set<DomainEvent> domainEvents;


  public Expense(
      final ExpenseId anId,
      final String aDescription,
      final LocalDateTime aCreationDateTime,
      final ExpenseCategory aCategory,
      final MonetaryAmount anAmount,
      final UserId aUserId) {

    checkArgument(nonNull(anId), "expense id cannot be null");
    checkArgument(nonNull(aCreationDateTime), "expense creation date time cannot be null");
    checkArgument(nonNull(aCategory), "expense category cannot be null");
    checkArgument(nonNull(anAmount), "expense amount cannot be null");
    checkArgument(nonNull(aUserId), "expense user id cannot be null");
    checkArgument(isNotEmpty(aDescription), "expense description cannot be null or empty");

    this.id = anId;
    this.description = aDescription;
    this.creationDateTime = aCreationDateTime;
    this.category = aCategory;
    this.amount = anAmount;
    this.userId = aUserId;

    this.domainEvents =  new HashSet<>();;
  }

  public static Expense addExpense(
      final ExpenseId anId,
      final String aDescription,
      final LocalDateTime aCreationDateTime,
      final ExpenseCategory aCategory,
      final MonetaryAmount anAmount,
      final UserId aUserId) {

    var aNewExpense = new Expense(
        anId,
        aDescription,
        aCreationDateTime,
        aCategory,
        anAmount,
        aUserId);

    var expenseAddedEvent = new ExpenseEvent.ExpenseAdded(
        aNewExpense.getId().getValue(),
        aNewExpense.getUserId().getId(),
        aNewExpense.getCategory().name(),
        aNewExpense.getAmount().getNumber().doubleValueExact(),
        aNewExpense.getAmount().getCurrency().getCurrencyCode()
    );
    aNewExpense.domainEvents.add(expenseAddedEvent);
    return aNewExpense;
  }

  public void updateExpense(final String theNewDescription,
      final ExpenseCategory theNewExpenseCategory,
      final MonetaryAmount theNewMonetaryAmount) {

    checkArgument(isNotEmpty(theNewDescription), "expense description cannot be null or empty");
    checkArgument(nonNull(theNewExpenseCategory), "expense category cannot be null");
    checkArgument(nonNull(theNewMonetaryAmount), "expense amount cannot be null");

    this.category = theNewExpenseCategory;
    this.description = theNewDescription;
    this.amount = theNewMonetaryAmount;

    var expenseUpdatedEvent = new ExpenseEvent.ExpenseUpdated(
        this.getId().getValue(),
        this.getUserId().getId(),
        this.getCategory().name(),
        this.getAmount().getNumber().doubleValueExact(),
        this.getAmount().getCurrency().getCurrencyCode()
    );
    this.domainEvents.add(expenseUpdatedEvent);
  }
}
