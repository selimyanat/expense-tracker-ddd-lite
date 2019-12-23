package com.sy.expense.tracker.identity.application.command;

import com.sy.expense.tracker.identity.domain.Password;
import com.sy.expense.tracker.identity.domain.UserId;
import lombok.Value;

/**
 * Represents a change user password command.
 *
 * @author selim
 */
@Value
public class ChangeUserPasswordCommand {

  private final UserId userId;

  private final Password currentPasswordInPlainText;

  private final Password changedPasswordInPlainText;

}
