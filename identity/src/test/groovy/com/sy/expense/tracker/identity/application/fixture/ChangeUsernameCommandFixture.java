package com.sy.expense.tracker.identity.application.fixture;

import com.sy.expense.tracker.identity.application.command.ChangeUsernameCommand;
import com.sy.expense.tracker.identity.domain.User;
import com.sy.expense.tracker.identity.domain.UserId;

/**
 * Return prepared instance of {@link ChangeUsernameCommand} to use during testing.
 */
public final class ChangeUsernameCommandFixture {

  private ChangeUsernameCommandFixture() {
  }

  public static ChangeUsernameCommand aChangeUsernameCommand(User aUser) {

    return new ChangeUsernameCommand(
        new UserId(aUser.getId().getValue()),
        aUser.getUsername());
  }

}
