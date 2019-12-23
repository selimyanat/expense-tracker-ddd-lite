package com.sy.expense.tracker.identity.application;

import com.sy.expense.tracker.identity.application.command.AuthenticationUserCommand;
import com.sy.expense.tracker.identity.application.command.ChangeUserPasswordCommand;
import com.sy.expense.tracker.identity.application.command.ChangeUsernameCommand;
import com.sy.expense.tracker.identity.application.command.GetUserByEmailCommand;
import com.sy.expense.tracker.identity.application.command.RegisterUserCommand;
import com.sy.expense.tracker.identity.domain.AuthenticationService;
import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.EncryptionService;
import com.sy.expense.tracker.identity.domain.User;
import com.sy.expense.tracker.identity.domain.UserDescriptor;
import com.sy.expense.tracker.identity.domain.UserId;
import com.sy.expense.tracker.identity.domain.UserRepository;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
public class IdentityApplicationService {

  private final UserRepository userRepository;

  private final EncryptionService encryptionService;

  private final DomainEventPublisher domainEventPublisher;

  private final AuthenticationService authenticationService;

  @Transactional
  public User registerUser(final RegisterUserCommand aCommand) {

    getUserByEmail(aCommand.getUserEmail()).ifPresent(user -> {
      throw new IllegalArgumentException(String.format("User with email %s already exit",
          aCommand.getUserEmail().getValue()));
    });

    var user = User.registerUser(aCommand.getUserId(),
        aCommand.getUsername(),
        aCommand.getPassword(),
        aCommand.getUserEmail(),
        encryptionService);

    userRepository.insertUser(user);
    this.publishEvents(user.getDomainEvents());
    return user;

  }

  @Transactional
  public void changeUsername(final ChangeUsernameCommand aCommand) {

    var existingUser = this.existingUser(aCommand.getUserId());
    existingUser.changeUsername(aCommand.getChangedUsername());

    // TODO add / call update operation instead
    this.userRepository.insertUser(existingUser);
    this.publishEvents(existingUser.getDomainEvents());
  }

  @Transactional
  public void changeUserPassword(final ChangeUserPasswordCommand aCommand) {

    var existingUser = this.existingUser(aCommand.getUserId());
    existingUser.changePassword(aCommand.getCurrentPasswordInPlainText(),
        aCommand.getChangedPasswordInPlainText(), encryptionService);

    // TODO add / call update operation instead
    this.userRepository.insertUser(existingUser);
    this.publishEvents(existingUser.getDomainEvents());
  }

  public Optional<UserDescriptor> authenticateUser(
      final AuthenticationUserCommand authenticationUserCommand) {

    return authenticationService.authenticate(authenticationUserCommand.getUserEmail(),
        authenticationUserCommand.getUserPassword());
  }

  public Optional<User> getUserByEmail(GetUserByEmailCommand getUserByEmailCommand) {

    return getUserByEmail(getUserByEmailCommand.getUserEmail());
  }

  private Optional<User> getUserByEmail(Email email) {

    return this.userRepository.findByUserEmail(email);
  }

  private User existingUser(final UserId aUserId) {

    return this.userRepository.findByUserId(aUserId)
        .orElseThrow(
            () -> new IllegalArgumentException("User with given Id does not exists: " + aUserId));
  }

  private void publishEvents(Set<DomainEvent> theEvents) {

    this.domainEventPublisher.publish(theEvents);
  }

}
