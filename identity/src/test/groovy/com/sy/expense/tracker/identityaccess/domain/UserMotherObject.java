package com.sy.expense.tracker.identityaccess.domain;

import java.util.UUID;

/**
 * Return prepared instance of {@link User} to use during testing.
 */
public final class UserMotherObject {

  private UserMotherObject() {}

  public static final User aUser() {

    return new User(
            new UserId(UUID.randomUUID()),
            new Username("aUser"),
            new EncryptedPassword("anEncryptedPassword"),
            new Email("aUser@somewhere.com"));
  }
}
