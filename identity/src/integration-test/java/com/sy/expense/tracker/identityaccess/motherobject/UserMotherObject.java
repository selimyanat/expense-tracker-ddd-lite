package com.sy.expense.tracker.identityaccess.motherobject;

import com.sy.expense.tracker.identityaccess.domain.Email;
import com.sy.expense.tracker.identityaccess.domain.EncryptedPassword;
import com.sy.expense.tracker.identityaccess.domain.User;
import com.sy.expense.tracker.identityaccess.domain.UserId;
import com.sy.expense.tracker.identityaccess.domain.Username;
import java.util.UUID;

/**
 * Return prepared instance of {@link User} to use during testing.
 */
public class UserMotherObject {

  private UserMotherObject() {}

  public static final User firstDatabaseUser() {

    return new User(
        new UserId(UUID.fromString("ff654fe9-c349-4370-a472-8096552aec5f")),
        new Username("user1"),
        new EncryptedPassword("erfn23fflfdf"),
        new Email("user1@earth.com"));
  }

  public static final User aUserToCreate() {

    return new User(
        new UserId(UUID.fromString("cc774fe9-d888-4377-a475-9096552aec5c")),
        new Username("user2"),
        new EncryptedPassword("anEncryptedPassword"),
        new Email("user2@somewhere.com"));
  }

}
