package com.sy.expense.tracker.expense.infrastructure;


import static com.sy.expense.tracker.expense.application.fixture.ExpenseFixture.anExpenseToCreate;
import static com.sy.expense.tracker.expense.application.fixture.ExpenseFixture.anExpenseToUpdate;
import static com.sy.expense.tracker.expense.application.fixture.ExpenseFixture.firstDatabaseExpense;
import static com.sy.expense.tracker.expense.application.fixture.ExpenseFixture.secondDatabaseExpense;
import static org.assertj.core.api.Assertions.assertThat;

import com.sy.expense.tracker.expense.domain.ExpenseId;
import com.sy.expense.tracker.expense.domain.UserId;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Integration test for {@link JdbcExpenseRepositoryImpl}
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@JdbcTest
@Sql(
    statements = {"insert into expenses ("
        + "expense_uuid, "
        + "user_uuid, "
        + "expense_description, "
        + "expense_creation_date_time, "
        + "expense_category, "
        + "expense_amount, "
        + "expense_currency) "
        + "values ("
        + "'cc654fe9-c239-4272-a002-8096552aec5f', "
        + "'ff654fe9-c349-4370-a472-8096552aec5f', "
        + "'return from airport', "
        + "1586637660, "
        + "2, "
        + "50.50, "
        + "'USD'"
        + ") ",
        "insert into expenses ("
            + "expense_uuid, "
            + "user_uuid, "
            + "expense_description, "
            + "expense_creation_date_time, "
            + "expense_category, "
            + "expense_amount, "
            + "expense_currency) "
            + "values ("
            + "'cc994fe9-c339-5586-b002-9996552aec6c', "
            + "'ff654fe9-c349-4370-a472-8096552aec5f', "
            + "'an opera', "
            + "1586637660, "
            + "3, "
            + "150.50, "
            + "'USD'"
            + ") "
    })
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:/expense.properties")
public class JdbcExpenseRepositoryIT {

  @Autowired
  @Qualifier("expenseRepository")
  private JdbcExpenseRepositoryImpl underTest;

  @Test
  public void findByExpenseId_when_expense_exists_returnsExpense() {

    assertThat(underTest.findByExpenseId(new ExpenseId(UUID
        .fromString("cc654fe9-c239-4272-a002-8096552aec5f"))))
        .isNotEmpty()
        .contains(firstDatabaseExpense());
  }

  @Test
  public void findByExpenseId_when_expense_doesNot_exist_returnsEmpty() {

    assertThat(underTest.findByExpenseId(new ExpenseId(UUID
        .fromString("ee654fe4-c239-5274-a002-8096552aec5f"))))
        .isEmpty();
  }

  @Test
  public void findByUserId_when_userId_exist_returnsExpenses() {

    var theExpenses = underTest.findByUserId(new UserId("ff654fe9-c349-4370-a472"
        + "-8096552aec5f"));

    assertThat(theExpenses)
        .hasSize(2)
        .contains(firstDatabaseExpense(), secondDatabaseExpense());
  }

  @Test
  public void findByUserId_when_userId_doesNot_exist_returnsEmptyList() {

    assertThat(underTest.findByUserId(new UserId("aa654fe9-c349-4370-a483-8096552aec5f")))
        .isEmpty();
  }

  @Test
  public void insertExpense_isOk() {

    var anExpense = anExpenseToCreate();

    underTest.insertExpense(anExpense);
    assertThat(underTest.findByExpenseId(anExpense.getId()))
        .isNotEmpty()
        .contains(anExpense);
  }

  @Test
  public void updateExpense_isOk() {

    var anExpenseToUpdate = anExpenseToUpdate();

    underTest.updateExpensePartially(anExpenseToUpdate);
    assertThat(underTest.findByExpenseId(anExpenseToUpdate.getId()))
        .isNotEmpty()
        .hasValueSatisfying(expense -> {
          assertThat(expense.getDescription()).isEqualTo(anExpenseToUpdate.getDescription());
          assertThat(expense.getCategory()).isEqualTo(anExpenseToUpdate.getCategory());
          assertThat(expense.getAmount()).isEqualTo(anExpenseToUpdate.getAmount());
        });
  }


  @Configuration
  @Import({ExpenseDatabaseConfig.class, JdbcExpenseRepositoryImpl.class})
  static class TestContextConfiguration {

  }

}
