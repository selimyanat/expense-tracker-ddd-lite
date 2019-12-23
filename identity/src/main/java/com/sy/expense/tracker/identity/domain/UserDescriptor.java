package com.sy.expense.tracker.identity.domain;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class UserDescriptor {

  private final UserId userId;

  private final String username;

  private final Email email;

}
