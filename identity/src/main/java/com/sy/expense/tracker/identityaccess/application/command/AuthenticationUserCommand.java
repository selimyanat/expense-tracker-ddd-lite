package com.sy.expense.tracker.identityaccess.application.command;

import com.sy.expense.tracker.identityaccess.domain.Email;
import com.sy.expense.tracker.identityaccess.domain.Password;
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
