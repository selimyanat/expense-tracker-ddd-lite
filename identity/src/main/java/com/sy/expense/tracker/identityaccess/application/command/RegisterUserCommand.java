package com.sy.expense.tracker.identityaccess.application.command;

import com.sy.expense.tracker.identityaccess.domain.Email;
import com.sy.expense.tracker.identityaccess.domain.Password;
import com.sy.expense.tracker.identityaccess.domain.UserId;
import com.sy.expense.tracker.identityaccess.domain.Username;
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

  private final Password password;

  private final Email userEmail;

}
