package com.sy.expense.tracker.expense.application


import com.sy.expense.tracker.expense.domain.Expense
import com.sy.expense.tracker.expense.domain.fixture.ExpenseFixture
import com.sy.expense.tracker.expense.domain.ExpenseRepository
import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher
import spock.lang.Specification

/**
 * Test class for {@link com.sy.expense.tracker.expense.application.ExpenseApplicationService}
 */
class ExpenseApplicationServiceSpec extends Specification {

    ExpenseRepository expenseRepository = Mock()

    DomainEventPublisher domainEventPublisher = Mock()

    ExpenseApplicationService underTest = new ExpenseApplicationService(expenseRepository,
            domainEventPublisher);

    def 'Should add an expense'() {
        given:
            def aCommand = com.sy.expense.tracker.expense.application.fixture.AddExpenseCommandFixture.aTaxiExpenseCommand()
        when:
            underTest.addExpense(aCommand)
        then:
        1 * expenseRepository.insertExpense({ Expense anExpense ->
            anExpense.getId() == aCommand.getExpenseId()
            anExpense.getUserId() == aCommand.getUserId()
            anExpense.getCreationDateTime() == aCommand.getCreationDateTime()
            anExpense.getDescription() == aCommand.getDescription()
            anExpense.getCategory() == aCommand.getCategory()
            anExpense.getAmount() == aCommand.getAmount()
        })
        1 * domainEventPublisher.publish(*_)
    }

    def 'Should update an expense'() {
        given:
            def anExpense = ExpenseFixture.aHotelExpense()
            def aCommand = com.sy.expense.tracker.expense.application.fixture.UpdateExpenseCommandFixture.aTravelUpdateExpenseCommand()
        and:
            expenseRepository.findByExpenseId(aCommand.getExpenseId()) >> Optional.of(anExpense)
        when:
            underTest.updateExpense(aCommand)
        then:
        1 * expenseRepository.updateExpensePartially({ Expense expenseToUpdate ->
            expenseToUpdate.getId() == anExpense.getId()
            expenseToUpdate.getUserId() == anExpense.getUserId()
            expenseToUpdate.getCreationDateTime() == anExpense.getCreationDateTime()
            expenseToUpdate.getDescription() == aCommand.getDescription()
            expenseToUpdate.getCategory() == aCommand.getCategory()
            expenseToUpdate.getAmount() == aCommand.getAmount()
        })
        1 * domainEventPublisher.publish(*_)
    }

    def 'Should not update an expense if it does not exist'() {
        given:
            def aCommand = com.sy.expense.tracker.expense.application.fixture.UpdateExpenseCommandFixture.aTravelUpdateExpenseCommand()
        and:
            expenseRepository.findByExpenseId(aCommand.getExpenseId()) >> Optional.empty()
        when:
            underTest.updateExpense(aCommand)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "Expense with given Id does not exists: " + aCommand
                    .getExpenseId()
    }

}
