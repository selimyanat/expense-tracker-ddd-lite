package com.sy.expense.tracker.identity.api;

import static com.sy.expense.tracker.identity.api.IdentityController.CREATE_USER_URL;
import static com.sy.expense.tracker.identity.api.IdentityController.GET_USER_URL;
import static com.sy.expense.tracker.identity.api.IdentityController.IDENTITY_ROOT_URL;
import static com.sy.expense.tracker.identity.api.CreateUserRequestFixture.createUser1Request;
import static com.sy.expense.tracker.identity.fixture.UserFixture.aUserToCreate;
import static com.sy.expense.tracker.identity.fixture.UserFixture.firstDatabaseUser;
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
import com.sy.expense.tracker.identity.application.IdentityApplicationService;
import com.sy.expense.tracker.identity.application.command.GetUserByEmailCommand;
import com.sy.expense.tracker.identity.application.command.RegisterUserCommand;
import java.util.Optional;
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
@WebMvcTest(controllers = IdentityController.class)
@WithMockUser
public class IdentityControllerIT {

  @Autowired
  protected MockMvc mvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @MockBean
  protected IdentityApplicationService identityApplicationService;

  @Test
  public void getUser_that_exist_return_200() throws Exception {

    var response = firstDatabaseUser();
    when(identityApplicationService.getUserByEmail(any(GetUserByEmailCommand.class)))
        .thenReturn(Optional.of(response));

    mvc.perform(get(IDENTITY_ROOT_URL + GET_USER_URL,
        "user1@somewhere.com")
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.email", is(response.getEmail().getValue())))
        .andExpect(jsonPath("$.userName", is(response.getUsername())));
  }

  @Test
  public void getUser_that_does_not_exist_returns_404() throws Exception {

    var response = firstDatabaseUser();
    when(identityApplicationService.getUserByEmail(any(GetUserByEmailCommand.class)))
        .thenReturn(Optional.empty());

    mvc.perform(get(IDENTITY_ROOT_URL + GET_USER_URL,
        "user1@somewhere.com")
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }


  @Test
  public void createUser_returns_201() throws Exception {

    var request = createUser1Request();
    var response = aUserToCreate();
    when(identityApplicationService.registerUser(any(RegisterUserCommand.class)))
        .thenReturn(response);

    mvc.perform(post(IDENTITY_ROOT_URL + CREATE_USER_URL)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.*", hasSize(2)))
        .andExpect(jsonPath("$.email", is(response.getEmail().getValue())))
        .andExpect(jsonPath("$.userName", is(response.getUsername())));
  }
}
