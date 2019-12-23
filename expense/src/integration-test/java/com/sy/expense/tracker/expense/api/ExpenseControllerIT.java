package com.sy.expense.tracker.expense.api;

import static com.sy.expense.tracker.expense.api.CreateUserExpenseRequestFixture.createUser1ExpenseRequest;
import static com.sy.expense.tracker.expense.api.ExpenseController.CREATE_EXPENSE_URL;
import static com.sy.expense.tracker.expense.api.ExpenseController.EXPENSE_ROOT_URL;
import static com.sy.expense.tracker.expense.api.ExpenseController.GET_USER_EXPENSES_URL;
import static com.sy.expense.tracker.expense.application.fixture.ExpenseFixture.anExpenseToCreate;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sy.expense.tracker.expense.application.ExpenseApplicationService;
import com.sy.expense.tracker.expense.application.command.AddExpenseCommand;
import com.sy.expense.tracker.expense.application.command.GetExpensesByUserIdCommand;
import com.sy.expense.tracker.expense.application.fixture.ExpenseFixture;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@WebMvcTest(controllers = ExpenseController.class)
@WithMockUser
public class ExpenseControllerIT {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @MockBean
  protected ExpenseApplicationService expenseApplicationService;

  @Test
  public void getUserExpenses_returns_allExpenses_alongside_200() throws Exception {

    var expense1 = ExpenseFixture.firstDatabaseExpense();
    var expense2 = ExpenseFixture.secondDatabaseExpense();
    when(expenseApplicationService.getExpenses(any(GetExpensesByUserIdCommand.class)))
        .thenReturn(List.of(expense1, expense2));

    mvc.perform(get(EXPENSE_ROOT_URL + GET_USER_EXPENSES_URL,
        "user1@somewhere.com")
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(2)))
        // expense 1
        .andExpect(jsonPath("$[0].*", hasSize(4)))
        .andExpect(jsonPath("$[0].email", is(expense1.getUserId().getId())))
        .andExpect(jsonPath("$[0].description", is(expense1.getDescription())))
        .andExpect(jsonPath("$[0].category", is(expense1.getCategory().name())))
        .andExpect(jsonPath("$[0].amount", is(expense1.getAmount().getNumber().doubleValue())))
        // expense 2
        .andExpect(jsonPath("$[1].*", hasSize(4)))
        .andExpect(jsonPath("$[1].email", is(expense2.getUserId().getId())))
        .andExpect(jsonPath("$[1].description", is(expense2.getDescription())))
        .andExpect(jsonPath("$[1].category", is(expense2.getCategory().name())))
        .andExpect(jsonPath("$[1].amount", is(expense2.getAmount().getNumber().doubleValue())));
  }

  @Test
  public void createUserExpense_returns_201() throws Exception {

    var request = createUser1ExpenseRequest();
    var response = anExpenseToCreate();
    when(expenseApplicationService.addExpense(any(AddExpenseCommand.class)))
        .thenReturn(response);

    mvc.perform(post(EXPENSE_ROOT_URL + CREATE_EXPENSE_URL)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.*", hasSize(4)))
        .andExpect(jsonPath("$.email", is(response.getUserId().getId())))
        .andExpect(jsonPath("$.description", is(response.getDescription())))
        .andExpect(jsonPath("$.category", is(response.getCategory().name())))
        .andExpect(jsonPath("$.amount", is(response.getAmount().getNumber().doubleValue())));
  }

}
