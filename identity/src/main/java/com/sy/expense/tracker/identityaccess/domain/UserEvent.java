package com.sy.expense.tracker.identityaccess.domain;

import com.sy.expense.tracker.sharedkernel.domain.AggregateId;
import com.sy.expense.tracker.sharedkernel.events.DomainEvent;
import com.sy.expense.tracker.sharedkernel.events.EventName;
import lombok.Value;

/**
 * Holds all user related events.
 *
 * @author selim
 */
public interface UserEvent {

  /**
   * Event representing a user registration.
   */
  @Value
  class UserRegistered extends DomainEvent {

    static final String USER_REGISTERED = "userRegistered";

    private final Username username;

    private final Email userEmail;

    public UserRegistered(UserId userId, Username username, Email userEmail) {

      super(new EventName(USER_REGISTERED), new AggregateId(userId.getValue()));
      this.username = username;
      this.userEmail = userEmail;
    }
  }

  /**
   * Event representing a username updated.
   */
  @Value
  class UserNameChanged extends DomainEvent {

    static final String USERNAME_CHANGED = "userNameChanged";

    private final Username formerUserName;

    private final Username newUserName;

    public UserNameChanged(final UserId userId, final Username formerUserName, final Username newUserName) {

      super(new EventName(USERNAME_CHANGED), new AggregateId(userId.getValue()));
      this.newUserName = newUserName;
      this.formerUserName = formerUserName;
    }
  }

  /**
   * Event representing a user password updated.
   */
  @Value
  class UserPasswordChanged extends DomainEvent {

    static final String USER_PASSWORD_CHANGED = "userPasswordChanged";

    public UserPasswordChanged(final UserId userId) {
      super(new EventName(USER_PASSWORD_CHANGED), new AggregateId(userId.getValue()));
    }
  }
}
