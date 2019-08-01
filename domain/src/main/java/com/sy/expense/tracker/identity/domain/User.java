package com.sy.expense.tracker.identity.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.sy.expense.tracker.identity.domain.UserEvent.UserNameChanged;
import com.sy.expense.tracker.identity.domain.UserEvent.UserPasswordChanged;
import com.sy.expense.tracker.sharedkernel.DomainEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a user with its core attributes.
 *
 * @author selim
 */
@EqualsAndHashCode(of = "id")
@Getter
public class User {

  private final UserId id;

  private Password password;

  private Username userName;

  private Email email;

  private final Set<DomainEvent> domainEvents;

  public User(final UserId id, final Username userName, final Password password,
      final Email email) {
    this.id = id;
    this.password = password;
    this.userName = userName;
    this.email = email;
    this.domainEvents = new HashSet<>();
  }

  public void changeUsername(final Username newUserName) {

    this.domainEvents.add(new UserNameChanged(this.id, this.userName, newUserName));
    this.userName = newUserName;
  }

  public void changePassword(final Password currentPassword, final Password newPassword,
      final EncryptionService encryptionService) {

    assertUserNamePasswordNotSame(newPassword);
    assertPasswordNotSame(currentPassword, encryptionService);

    this.domainEvents.add(new UserPasswordChanged(this.id));
    this.password = new Password(encryptionService.encrypt(newPassword.getValue()));
  }

  private void assertUserNamePasswordNotSame(final Password newPassword) {

    checkArgument(!this.userName.getValue().equals(newPassword.getValue()), "The user name and "
        + "password "
        + "must not be the same");
  }

  private void assertPasswordNotSame(final Password currentPassword, final EncryptionService encryptionService) {

    checkArgument(this.password.getValue().equals(encryptionService.encrypt(currentPassword.getValue())),
        "The current password does not match existing password");
  }
}
