package com.sy.expense.tracker.identity.application.command;

import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.Password;
import lombok.Value;

/**
 * Represents an attempt to authenticate a user.
 *
 * @author selim
 */
@Value
public class AuthenticationUserCommand {

  private final Email userEmail;

  private final Password userPassword;

}
