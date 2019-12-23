package com.sy.expense.tracker.identity.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import com.sy.expense.tracker.identity.domain.event.UserEvent.UserNameChanged;
import com.sy.expense.tracker.identity.domain.event.UserEvent.UserPasswordChanged;
import com.sy.expense.tracker.identity.domain.event.UserEvent.UserRegistered;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
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

  private final Set<DomainEvent> domainEvents;

  private EncryptedPassword encryptedPassword;

  private String username;

  private Email email;

  public User(
      final UserId anId,
      final String aUserName,
      final EncryptedPassword anEncryptedPassword,
      final Email anEmail) {

    checkArgument(nonNull(anId), "user id cannot be null");
    checkArgument(nonNull(anEncryptedPassword), "user password cannot be null");
    checkArgument(nonNull(anEmail), "user email cannot be null");
    checkArgument(isNotEmpty(aUserName), "user name cannot be null or empty");

    this.id = anId;
    this.encryptedPassword = anEncryptedPassword;
    this.username = aUserName;
    this.email = anEmail;
    this.domainEvents = new HashSet<>();
  }

  public static User registerUser(
      final UserId anId,
      final String aUserName,
      final Password aPassword,
      final Email anEmail,
      final EncryptionService anEncryptionService) {

    checkArgument(nonNull(anEncryptionService), "encryption service cannot be null");

    var user = new User(
        anId,
        aUserName,
        new EncryptedPassword(anEncryptionService.encrypt(aPassword.getValue())),
        anEmail);

    var userRegisteredEvent = new UserRegistered(
        user.getId().getValue(),
        user.getUsername(),
        user.getEmail().getValue());
    user.domainEvents.add(userRegisteredEvent);
    return user;
  }

  public void changeUsername(final String theNewUserName) {

    checkArgument(isNotEmpty(theNewUserName), "user name cannot be null or empty");

    var formerUsername = this.username;
    this.username = theNewUserName;
    this.domainEvents.add(new UserNameChanged(
        this.id.getValue(),
        formerUsername,
        theNewUserName));
  }

  public void changePassword(
      final Password theCurrentPassword,
      final Password theNewPassword,
      final EncryptionService anEncryptionService) {

    checkArgument(nonNull(theCurrentPassword), "user id cannot be null");
    checkArgument(nonNull(theNewPassword), "user id cannot be null");
    checkArgument(nonNull(anEncryptionService), "encryption service cannot be null");
    assertUserNamePasswordNotSame(theNewPassword);
    assertPasswordNotSame(theCurrentPassword, anEncryptionService);

    this.domainEvents.add(new UserPasswordChanged(this.id.getValue()));
    this.encryptedPassword = new EncryptedPassword(
        anEncryptionService.encrypt(theNewPassword.getValue()));
  }

  private void assertUserNamePasswordNotSame(final Password theNewPassword) {

    checkArgument(!this.username.equals(theNewPassword.getValue()),
        "The user name and encryptedPassword must not be the same");
  }

  private void assertPasswordNotSame(
      final Password aPasswordToCheck,
      final EncryptionService theEncryptionService) {

    checkArgument(
        this.encryptedPassword.getValue().equals(theEncryptionService.encrypt(aPasswordToCheck.getValue())),
        "The given password does not match existing password");
  }
}
