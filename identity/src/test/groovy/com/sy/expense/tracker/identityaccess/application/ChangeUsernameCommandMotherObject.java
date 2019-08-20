package com.sy.expense.tracker.identityaccess.application;

import com.sy.expense.tracker.identityaccess.application.command.ChangeUsernameCommand;
import com.sy.expense.tracker.identityaccess.domain.User;
import com.sy.expense.tracker.identityaccess.domain.UserId;
import com.sy.expense.tracker.identityaccess.domain.Username;

/**
 * Return prepared instance of {@link ChangeUsernameCommand} to use during testing.
 */
public final class ChangeUsernameCommandMotherObject {

  private ChangeUsernameCommandMotherObject() {}

  public static ChangeUsernameCommand aChangeUsernameCommand(User aUser) {

    return new ChangeUsernameCommand(
        new UserId(aUser.getId().getValue()),
        new Username(aUser.getUsername().getValue()));
  }

}
