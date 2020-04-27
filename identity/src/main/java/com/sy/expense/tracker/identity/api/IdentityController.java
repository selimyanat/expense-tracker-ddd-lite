package com.sy.expense.tracker.identity.api;

import com.sy.expense.tracker.identity.application.IdentityApplicationService;
import com.sy.expense.tracker.identity.application.command.GetUserByEmailCommand;
import com.sy.expense.tracker.identity.application.command.RegisterUserCommand;
import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.Password;
import com.sy.expense.tracker.identity.domain.UserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Api(tags = "Identity")
@RestController
@RequestMapping(path = IdentityController.IDENTITY_ROOT_URL)
public class IdentityController {

  public static final String IDENTITY_ROOT_URL = "/identity";

  public static final String CREATE_USER_URL = "";

  public static final String GET_USER_URL =  "/users/{userEmail}";

  private final IdentityApplicationService identityApplicationService;

  @ApiOperation(value = "Returns user information")
  @GetMapping(GET_USER_URL)
  public ResponseEntity<UserInformation> getUserInfo(
      @ApiParam(
          value = "A user email",
          required = true,
          example = "foo@bar.com")
      @PathVariable String userEmail) {

    var command = new GetUserByEmailCommand(new Email(userEmail));
    var user = identityApplicationService.getUserByEmail(command);

    return user
        .map(user1 -> new UserInformation()
            .setEmail(user.get().getEmail().getValue())
            .setUserName(user.get().getUsername()))
        .map(userInformation -> ResponseEntity.ok().body(userInformation))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @ApiOperation(value = "Create a user")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserInformation createUser(
      @ApiParam(
          value = "A create user request",
          required = true)
      @RequestBody CreateUserRequest createUserRequest) {

    var command = new RegisterUserCommand(
        new UserId(UUID.randomUUID()),
        createUserRequest.getUserName(),
        new Password(createUserRequest.getPassword()),
        new Email(createUserRequest.getEmail()));

    var user = identityApplicationService.registerUser(command);
    return new UserInformation()
        .setEmail(user.getEmail().getValue())
        .setUserName(user.getUsername());
  }

}
