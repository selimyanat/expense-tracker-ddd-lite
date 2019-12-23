package com.sy.expense.tracker.identity.application.fixture;

import com.sy.expense.tracker.identity.application.command.AuthenticationUserCommand;
import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.Password;

/**
 * Return prepared instance of {@link AuthenticationUserCommand} to use during testing.
 */
public final class AuthenticateUserCommandFixture {

  private AuthenticateUserCommandFixture() {
  }

  public static AuthenticationUserCommand anAuthenticationUserCommand() {

    return new AuthenticationUserCommand(
        new Email("user@somewhere.com"),
        new Password("aPassword"));
  }

}
