package com.sy.expense.tracker.identityaccess.domain;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Value
@Slf4j
public class UserDescriptor {

  private final UserId userId;

  private final Username username;

  private final Email email;

}
