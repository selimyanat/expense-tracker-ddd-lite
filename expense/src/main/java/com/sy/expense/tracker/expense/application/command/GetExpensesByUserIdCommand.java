package com.sy.expense.tracker.expense.application.command;

import lombok.Value;

/**
 * Represents a command to get a user.
 *
 * @author selim
 */
@Value
public class GetExpensesByUserIdCommand {

  private String userId;

}
