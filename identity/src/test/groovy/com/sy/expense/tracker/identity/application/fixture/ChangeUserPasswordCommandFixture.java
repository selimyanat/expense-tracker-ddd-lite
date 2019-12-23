package com.sy.expense.tracker.identity.application.fixture;

import com.sy.expense.tracker.identity.application.command.ChangeUserPasswordCommand;
import com.sy.expense.tracker.identity.domain.Password;
import com.sy.expense.tracker.identity.domain.User;
import com.sy.expense.tracker.identity.domain.UserId;

/**
 * Return prepared instance of {@link ChangeUserPasswordCommand} to use during testing.
 */
public final class ChangeUserPasswordCommandFixture {

  private ChangeUserPasswordCommandFixture() {
  }

  public static ChangeUserPasswordCommand aChangeUserPasswordCommand(User aUser) {

    return new ChangeUserPasswordCommand(
        new UserId(aUser.getId().getValue()),
        new Password(aUser.getEncryptedPassword().getValue()),
        new Password("anotherPassword"));
  }

}
