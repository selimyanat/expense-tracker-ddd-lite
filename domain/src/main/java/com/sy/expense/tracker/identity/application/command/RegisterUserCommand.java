package com.sy.expense.tracker.identity.application.command;

import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.Password;
import com.sy.expense.tracker.identity.domain.UserId;
import com.sy.expense.tracker.identity.domain.Username;
import lombok.Value;

/**
 * Represents a user registration command.
 *
 * @author selim
 */
@Value
public class RegisterUserCommand {

  private final UserId userId;

  private final Username username;

  private final Password userPassword;

  private final Email userEmail;

}
