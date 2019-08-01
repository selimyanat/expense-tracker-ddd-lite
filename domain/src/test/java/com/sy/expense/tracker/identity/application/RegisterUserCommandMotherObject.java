package com.sy.expense.tracker.identity.application;

import com.sy.expense.tracker.identity.application.command.RegisterUserCommand;
import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.Password;
import com.sy.expense.tracker.identity.domain.UserId;
import com.sy.expense.tracker.identity.domain.Username;
import java.util.UUID;

/**
 * Return prepared instance of {@link RegisterUserCommand} to use during testing.
 */
public final class RegisterUserCommandMotherObject {

  private RegisterUserCommandMotherObject() {}

  public static RegisterUserCommand aRegisterUserCommand() {

    return new RegisterUserCommand(
        new UserId(UUID.randomUUID()),
        new Username("aUsername"),
        new Password("aPassword"),
        new Email("user@somewhere.com"));
  }

}
