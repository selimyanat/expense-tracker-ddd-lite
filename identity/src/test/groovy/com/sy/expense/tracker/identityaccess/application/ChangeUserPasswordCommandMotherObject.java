package com.sy.expense.tracker.identityaccess.application;

import com.sy.expense.tracker.identityaccess.application.command.ChangeUserPasswordCommand;
import com.sy.expense.tracker.identityaccess.domain.Password;
import com.sy.expense.tracker.identityaccess.domain.User;
import com.sy.expense.tracker.identityaccess.domain.UserId;

/**
 * Return prepared instance of {@link ChangeUserPasswordCommand} to use during testing.
 */
public final class ChangeUserPasswordCommandMotherObject {

  private ChangeUserPasswordCommandMotherObject() {}

  public static ChangeUserPasswordCommand aChangeUserPasswordCommand(User aUser) {

    return new ChangeUserPasswordCommand(
        new UserId(aUser.getId().getValue()),
        new Password(aUser.getEncryptedPassword().getValue()),
        new Password("anotherPassword"));
  }

}
