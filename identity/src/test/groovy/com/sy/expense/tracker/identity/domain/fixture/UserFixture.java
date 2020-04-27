package com.sy.expense.tracker.identity.domain.fixture;

import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.EncryptedPassword;
import com.sy.expense.tracker.identity.domain.User;
import com.sy.expense.tracker.identity.domain.UserId;
import java.util.UUID;

/**
 * Return prepared instance of {@link User} to use during testing.
 */
public final class UserFixture {

  private UserFixture() {
  }

  public static final User aUser() {

    return new User(
        new UserId(UUID.randomUUID()),
        "aUser",
        new EncryptedPassword("anEncryptedPassword"),
        new Email("aUser@somewhere.com"));
  }
}
