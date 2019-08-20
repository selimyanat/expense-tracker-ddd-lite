package com.sy.expense.tracker.identityaccess.application;

import com.sy.expense.tracker.identityaccess.application.command.AuthenticationUserCommand;
import com.sy.expense.tracker.identityaccess.application.command.ChangeUserPasswordCommand;
import com.sy.expense.tracker.identityaccess.application.command.ChangeUsernameCommand;
import com.sy.expense.tracker.identityaccess.application.command.RegisterUserCommand;
import com.sy.expense.tracker.identityaccess.domain.AuthenticationService;
import com.sy.expense.tracker.identityaccess.domain.EncryptionService;
import com.sy.expense.tracker.identityaccess.domain.User;
import com.sy.expense.tracker.identityaccess.domain.UserDescriptor;
import com.sy.expense.tracker.identityaccess.domain.UserId;
import com.sy.expense.tracker.identityaccess.domain.UserRepository;
import com.sy.expense.tracker.sharedkernel.events.DomainEvent;
import com.sy.expense.tracker.sharedkernel.events.DomainEventPublisher;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class IdentityApplicationService {

  private final UserRepository userRepository;

  private final EncryptionService encryptionService;

  private final DomainEventPublisher domainEventPublisher;

  private final AuthenticationService authenticationService;

  public User registerUser(final RegisterUserCommand aCommand) {

    var user = User.registerUser(aCommand.getUserId(),
        aCommand.getUsername(),
        aCommand.getPassword(),
        aCommand.getUserEmail(),
        encryptionService);
    userRepository.insertUser(user);

    this.publishEvents(user.getDomainEvents());
    return user;

  }

  public void changeUsername(final ChangeUsernameCommand aCommand) {

    var existingUser = this.existingUser(aCommand.getUserId());
    existingUser.changeUsername(aCommand.getChangedUsername());

    this.userRepository.insertUser(existingUser);
    this.publishEvents(existingUser.getDomainEvents());
  }

  public void changeUserPassword(final ChangeUserPasswordCommand aCommand) {

    var existingUser = this.existingUser(aCommand.getUserId());
    existingUser.changePassword(aCommand.getCurrentPasswordInPlainText(), aCommand.getChangedPasswordInPlainText(), encryptionService);

    this.userRepository.insertUser(existingUser);
    this.publishEvents(existingUser.getDomainEvents());
  }

  public Optional<UserDescriptor> authenticateUser(final AuthenticationUserCommand authenticationUserCommand) {

    return authenticationService.authenticate(authenticationUserCommand.getUserEmail(),
        authenticationUserCommand.getUserPassword());
  }

  private User existingUser(final UserId aUserId) {

    return this.userRepository.findByUserId(aUserId)
        .orElseThrow(() -> new IllegalArgumentException("User with given Id does not exists: " + aUserId));
  }

  private void publishEvents(Set<DomainEvent> theEvents) {

    this.domainEventPublisher.publish(theEvents);
  }

}
