package com.sy.expense.tracker.expense.api;

import static com.sy.expense.tracker.expense.api.ExpenseController.EXPENSE_ROOT_URL;

import com.sy.expense.tracker.expense.application.ExpenseApplicationService;
import com.sy.expense.tracker.expense.application.command.AddExpenseCommand;
import com.sy.expense.tracker.expense.application.command.GetExpensesByUserIdCommand;
import com.sy.expense.tracker.expense.domain.Expense;
import com.sy.expense.tracker.expense.domain.ExpenseId;
import com.sy.expense.tracker.expense.domain.UserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.money.Monetary;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Api(tags = "Expenses")
@RestController
@RequestMapping(path = EXPENSE_ROOT_URL)
public class ExpenseController {

  public static final String EXPENSE_ROOT_URL = "/expenses";

  public static final String CREATE_EXPENSE_URL = "";

  public static final String GET_USER_EXPENSES_URL =  "/{userEmail}";

  private final ExpenseApplicationService expenseApplicationService;

  @ApiOperation(value = "Returns user expenses")
  @GetMapping(GET_USER_EXPENSES_URL)
  public List<UserExpense> getUserExpenses(
      @ApiParam(
          value = "A user email",
          required = true,
          example = "foo@bar.com")
      @PathVariable String userEmail) {

    var aCommand  = new GetExpensesByUserIdCommand(userEmail);
    return expenseApplicationService.getExpenses(aCommand)
        .stream()
        .map(expense -> mapToUserExpense(expense))
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "Create a user expense")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserExpense createUserExpense(@ApiParam(
      value = "A create user request",
      required = true)
  @RequestBody CreateUserExpenseRequest createUserExpenseRequest) {

    var aCommand = new AddExpenseCommand(
        new ExpenseId(UUID.randomUUID()),
        createUserExpenseRequest.getDescription(),
        LocalDateTime.now(),
        new UserId(createUserExpenseRequest.getEmail()),
        createUserExpenseRequest.getCategory(),
        Monetary.getDefaultAmountFactory()
            .setCurrency("USD")
            .setNumber(createUserExpenseRequest.getAmount())
            .create());
    var expense = expenseApplicationService.addExpense(aCommand);
    return mapToUserExpense(expense);
  }

  private static UserExpense mapToUserExpense(Expense expense) {

    return new UserExpense()
        .setEmail(expense.getUserId().getId())
        .setCategory(expense.getCategory())
        .setDescription(expense.getDescription())
        .setAmount(expense.getAmount().getNumber().doubleValueExact());
  }
}
