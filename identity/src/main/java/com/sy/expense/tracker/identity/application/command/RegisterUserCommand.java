package com.sy.expense.tracker.identity.application.command;

import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.Password;
import com.sy.expense.tracker.identity.domain.UserId;
import lombok.Value;

/**
 * Represents a user registration command.
 *
 * @author selim
 */
@Value
public class RegisterUserCommand {

  private final UserId userId;

  private final String username;

  private final Password password;

  private final Email userEmail;

}
