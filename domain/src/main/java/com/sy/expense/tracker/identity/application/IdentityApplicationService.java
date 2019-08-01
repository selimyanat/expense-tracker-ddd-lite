package com.sy.expense.tracker.identity.application;

import com.sy.expense.tracker.identity.application.command.ChangeUserPasswordCommand;
import com.sy.expense.tracker.identity.application.command.ChangeUsernameCommand;
import com.sy.expense.tracker.identity.application.command.RegisterUserCommand;
import com.sy.expense.tracker.identity.domain.EncryptionService;
import com.sy.expense.tracker.identity.domain.User;
import com.sy.expense.tracker.identity.domain.UserEvent.UserRegistered;
import com.sy.expense.tracker.identity.domain.UserId;
import com.sy.expense.tracker.identity.domain.UserRepository;
import com.sy.expense.tracker.sharedkernel.DomainEvent;
import com.sy.expense.tracker.sharedkernel.DomainEventPublisher;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IdentityApplicationService {

  private final UserRepository userRepository;

  private final EncryptionService encryptionService;

  private final DomainEventPublisher domainEventPublisher;

  public User registerUser(RegisterUserCommand command) {
    // TODO Enclose with @Transactional

    var user = new User(
        command.getUserId(),
        command.getUsername(),
        command.getUserPassword(),
        command.getUserEmail());
    userRepository.save(user);

    var userRegisteredEvent = new UserRegistered(user.getId(), user.getUserName(), user.getEmail());
    this.publishEvent(userRegisteredEvent);
    return user;

  }

  public void changeUsername(ChangeUsernameCommand command) {
    // TODO Enclose with @Transactional

    var user = this.existingUser(command.getUserId());
    user.changeUsername(command.getChangedUsername());

    this.publishEvents(user.getDomainEvents());
  }

  public void changeUserPassword(ChangeUserPasswordCommand command) {
    // TODO Enclose with @Transactional
    var user = this.existingUser(command.getUserId());
    user.changePassword(command.getCurrentPassword(), command.getChangedPassword(), encryptionService);

    this.publishEvents(user.getDomainEvents());
  }

  private User existingUser(UserId userId) {

    return userRepository.findByUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("User with given Id does not exists: " + userId));
  }

  private void publishEvent(DomainEvent event) {

    this.publishEvents(Set.of(event));
  }

  private void publishEvents(Set<DomainEvent> events) {

    domainEventPublisher.publish(events);
  }

}
