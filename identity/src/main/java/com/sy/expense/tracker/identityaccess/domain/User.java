package com.sy.expense.tracker.identityaccess.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.sy.expense.tracker.identityaccess.domain.UserEvent.UserNameChanged;
import com.sy.expense.tracker.identityaccess.domain.UserEvent.UserPasswordChanged;
import com.sy.expense.tracker.identityaccess.domain.UserEvent.UserRegistered;
import com.sy.expense.tracker.sharedkernel.events.DomainEvent;
import java.util.HashSet;
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

  private EncryptedPassword encryptedPassword;

  private Username username;

  private Email email;

  private final Set<DomainEvent> domainEvents;

  public User(final UserId id, final Username aUserName, final EncryptedPassword anEncryptedPassword,
      final Email anEmail) {

    this.id = id;
    this.encryptedPassword = anEncryptedPassword;
    this.username = aUserName;
    this.email = anEmail;
    this.domainEvents = new HashSet<>();
  }
  
  public static User registerUser(final UserId anId, final Username aUserName,
      final Password aPassword, final Email anEmail,
      final EncryptionService encryptionService) {

    var user = new User(
        anId,
        aUserName,
        new EncryptedPassword(encryptionService.encrypt(aPassword.getValue())),
        anEmail);

    var userRegisteredEvent = new UserRegistered(user.getId(), user.getUsername(), user.getEmail());
    user.domainEvents.add(userRegisteredEvent);
    return user;
  }

  public void changeUsername(final Username newUserName) {

    var formerUsername = this.username;
    this.username = newUserName;
    this.domainEvents.add(new UserNameChanged(this.id, formerUsername, newUserName));
  }

  public void changePassword(final Password currentPassword, final Password newPassword,
      final EncryptionService encryptionService) {

    assertUserNamePasswordNotSame(newPassword);
    assertPasswordNotSame(currentPassword, encryptionService);

    this.domainEvents.add(new UserPasswordChanged(this.id));
    this.encryptedPassword = new EncryptedPassword(encryptionService.encrypt(newPassword.getValue()));
  }

  private void assertUserNamePasswordNotSame(final Password newPassword) {

    checkArgument(!this.username.getValue().equals(newPassword.getValue()),
        "The user name and encryptedPassword must not be the same");
  }

  private void assertPasswordNotSame(final Password aPassword, final EncryptionService encryptionService) {

    checkArgument(this.encryptedPassword.getValue().equals(encryptionService.encrypt(aPassword.getValue())),
        "The given password does not match existing password");
  }
}
