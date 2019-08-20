package com.sy.expense.tracker.identityaccess.application;

import com.sy.expense.tracker.identityaccess.application.command.AuthenticationUserCommand;
import com.sy.expense.tracker.identityaccess.domain.Email;
import com.sy.expense.tracker.identityaccess.domain.Password;

/**
 * Return prepared instance of {@link AuthenticationUserCommand} to use during testing.
 */
public final class AuthenticateUserCommandMotherObject {

  private AuthenticateUserCommandMotherObject() {}

  public static AuthenticationUserCommand anAuthenticationUserCommand() {

    return new AuthenticationUserCommand(
        new Email("user@somewhere.com"),
        new Password("aPassword"));
  }

}
