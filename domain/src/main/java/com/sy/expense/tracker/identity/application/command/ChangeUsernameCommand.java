package com.sy.expense.tracker.identity.application.command;

import com.sy.expense.tracker.identity.domain.UserId;
import com.sy.expense.tracker.identity.domain.Username;
import lombok.Value;

/**
 * Represents a change username command.
 *
 * @author selim
 */
@Value
public class ChangeUsernameCommand {

  private final UserId userId;

  private final Username changedUsername;

}
