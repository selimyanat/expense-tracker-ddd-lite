package com.sy.expense.tracker.expense.domain

import com.sy.expense.tracker.expense.domain.event.ExpenseEvent
import com.sy.expense.tracker.expense.domain.fixture.ExpenseFixture
import spock.lang.Specification

import javax.money.Monetary
import java.time.LocalDateTime

/**
 * Test class for {@link Expense}
 */
class ExpenseSpec extends Specification{

    def 'Should create an expense'() {
        given:
            def anId = new ExpenseId(UUID.randomUUID())
            def aDescription = "Travel to Barcelona"
            def theCreationTime = LocalDateTime.now()
            def aCategory = ExpenseCategory.TRAVEL
            def anAmount = Monetary.getDefaultAmountFactory()
                    .setCurrency("USD")
                    .setNumber(120)
                    .create()
            def aUserId = new UserId(UUID.randomUUID().toString())
        when:
            def aExpense = Expense.addExpense(
                    anId,
                    aDescription,
                    theCreationTime,
                    aCategory,
                    anAmount,
                    aUserId)
        then:
            aExpense.getId() == anId
            aExpense.getDescription() == aDescription
            aExpense.getCreationDateTime() == theCreationTime
            aExpense.getCategory() == aCategory
            aExpense.getAmount() == anAmount
            aExpense.getUserId() == aUserId
        then:
            aExpense.getDomainEvents().size() == 1
            aExpense.getDomainEvents().contains(new ExpenseEvent.ExpenseAdded(
                aExpense.getId().getValue(),
                aExpense.getUserId().getId(),
                aExpense.getCategory().name(),
                aExpense.getAmount().getNumber().doubleValueExact(),
                aExpense.getAmount().getCurrency().getCurrencyCode())
            )
    }

    def 'Should update expense'() {
        def anExistingExpense = ExpenseFixture.aHotelExpense()
        def aNewDescription = "Travel to Barcelona"
        def aNewCategory = ExpenseCategory.TRAVEL
        def aNewAmount = Monetary.getDefaultAmountFactory()
                .setCurrency("USD")
                .setNumber(120)
                .create()
        when:
            anExistingExpense.updateExpense(aNewDescription, aNewCategory, aNewAmount)
        then:
            anExistingExpense.getDescription() == aNewDescription
            anExistingExpense.getCategory() == aNewCategory
            anExistingExpense.getAmount() == aNewAmount
        then:
            anExistingExpense.getDomainEvents().size() == 1
            anExistingExpense.getDomainEvents().contains(new ExpenseEvent.ExpenseUpdated(
                    anExistingExpense.getId().getValue(),
                    anExistingExpense.getUserId().getId(),
                    anExistingExpense.getCategory().name(),
                    anExistingExpense.getAmount().getNumber().doubleValueExact(),
                    anExistingExpense.getAmount().getCurrency().getCurrencyCode())
            )
    }

}
