package com.sy.expense.tracker.expense.infrastructure;

import com.sy.expense.tracker.expense.domain.Expense;
import com.sy.expense.tracker.expense.domain.ExpenseCategory;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import com.sy.expense.tracker.expense.domain.ExpenseRepository;
import com.sy.expense.tracker.expense.domain.UserId;
import io.vavr.control.Try;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.money.Monetary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * Jdbc repository implementation of {@link ExpenseRepository}
 */
@Slf4j
public class JdbcExpenseRepositoryImpl implements ExpenseRepository{

  private final JdbcTemplate jdbcTemplate;

  private final ExpenseRowMapper expenseRowMapper;

  private SimpleJdbcInsert simpleJdbcInsert;

  /**
   * Create a new instance of <code>{@link JdbcExpenseRepositoryImpl}</code>
   *
   * @param expenseJdbcTemplate the jdbc template to interact with the database
   */
  public JdbcExpenseRepositoryImpl(JdbcTemplate expenseJdbcTemplate) {

    this.jdbcTemplate = expenseJdbcTemplate;
    this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
    this.simpleJdbcInsert
        .withTableName("expenses")
        .usingGeneratedKeyColumns("expense_id");
    this.expenseRowMapper = new ExpenseRowMapper();
  }

  @Override
  public void insertExpense(Expense expense) {

    final Map<String, Object> parameters = new HashMap<>(5);
    parameters.put("expense_uuid", expense.getId().getValue());
    parameters.put("user_uuid", expense.getUserId().getId());
    parameters.put("expense_description", expense.getDescription());
    // TODO force UTC
    parameters.put("expense_creation_date_time", expense.getCreationDateTime()
            .atZone(ZoneId.systemDefault())
            .toEpochSecond());
    parameters.put("expense_category", expense.getCategory().getCode());
    parameters.put("expense_amount", expense.getAmount().getNumber().doubleValueExact());
    parameters.put("expense_currency", expense.getAmount().getCurrency().getCurrencyCode());

    Try.of(() -> this.simpleJdbcInsert.executeAndReturnKey(parameters))
        .onFailure(throwable -> LOG.warn("Fail to create expense with id {} ",
            expense.getId().getValue(), throwable))
        .onSuccess(id -> LOG.debug("Newly expense created in database with ID {} and UUID {}",
            id, expense.getId().getValue()));
  }

  @Override
  public void updateExpensePartially(Expense expense) {

    Try.of(() -> this.jdbcTemplate
        .update("update expenses set  "
            + " expense_description = ?, "
            + " expense_category = ?, "
            + " expense_amount = ?, "
            + " expense_currency = ? "
            + " where expense_uuid = ? ",
            expense.getDescription(),
            expense.getCategory().getCode(),
            expense.getAmount().getNumber().doubleValueExact(),
            expense.getAmount().getCurrency().getCurrencyCode(),
            expense.getId().getValue()))
        .onFailure(throwable -> LOG.warn("Fail to update expense with id {} ",
            expense.getId().getValue(), throwable))
        // TODO use either
        .andThen( count -> LOG.debug("Number of expense updated is: {}", count));
  }

  @Override
  public Optional<Expense> findByExpenseId(ExpenseId expenseId) {

    var result = Try.of(() -> this.jdbcTemplate
        .queryForObject("select * from expenses anExpense where anExpense.expense_uuid = ?",
            expenseRowMapper,
            expenseId.getValue().toString()
        ))
        .map(expense -> Optional.of(expense))
        .onFailure(throwable -> {
          var isEmptyResultException = throwable instanceof EmptyResultDataAccessException;
          if (!isEmptyResultException) {
            LOG.warn("Fail to read expense with id {} from database", expenseId.getValue(), throwable);
          }
        });

    return result.getOrElse(Optional.empty());
  }

  @Override
  public List<Expense> findByUserId(UserId userId) {

    var result = Try.of(() -> this.jdbcTemplate
        .query("select * from expenses where user_uuid = ?",
            new Object[]{userId.getId()},
            expenseRowMapper
        ))
        .onFailure(throwable -> LOG.warn("Fail to read expenses for user with id {} from database",
            userId.getId(), throwable));

    return result.getOrElse(Collections.emptyList());
  }

  private static class ExpenseRowMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {

      var expense = new Expense(
          new ExpenseId(UUID.fromString(rs.getString("expense_uuid"))),
          rs.getString("expense_description"),
          LocalDateTime.ofInstant(Instant.ofEpochSecond(rs.getLong("expense_creation_date_time")),
              ZoneId.systemDefault()),
          ExpenseCategory.fromCode(rs.getInt("expense_category")), Monetary
              .getDefaultAmountFactory()
              .setNumber(rs.getDouble("expense_amount"))
              .setCurrency(rs.getString("expense_currency"))
          .create(),
          new UserId(rs.getString("user_uuid"))
      );
      return expense;
    }
  }

}
