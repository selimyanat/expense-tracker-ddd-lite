package com.sy.expense.tracker.identity.application;

import com.sy.expense.tracker.identity.application.command.ChangeUsernameCommand;
import com.sy.expense.tracker.identity.domain.UserId;
import com.sy.expense.tracker.identity.domain.Username;
import java.util.UUID;

/**
 * Return prepared instance of {@link ChangeUsernameCommand} to use during testing.
 */
public final class ChangeUsernameCommandMotherObject {

  private ChangeUsernameCommandMotherObject() {}

  public static ChangeUsernameCommand aChangeUsernameCommand() {
    return new ChangeUsernameCommand(
        new UserId(UUID.randomUUID()),
        new Username("aUsername"));
  }

}
