package com.sy.expense.tracker.identity.application.command;

import com.sy.expense.tracker.identity.domain.Email;
import lombok.Value;

/**
 * Represents a command to get a user.
 *
 * @author selim
 */
@Value
public class GetUserByEmailCommand {

  private Email userEmail;
}
