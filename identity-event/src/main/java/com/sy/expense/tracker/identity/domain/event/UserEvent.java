package com.sy.expense.tracker.identity.domain.event;

import com.sy.expense.tracker.sharedkernel.domain.AggregateId;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import com.sy.expense.tracker.sharedkernel.domain.EventName;
import java.util.UUID;
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

    private final String username;

    private final String userEmail;

    public UserRegistered(UUID userId, String username, String userEmail) {

      super(new EventName(USER_REGISTERED), new AggregateId(userId));
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

    private final String formerUserName;

    private final String newUserName;

    public UserNameChanged(final UUID userId, final String formerUserName,
        final String newUserName) {

      super(new EventName(USERNAME_CHANGED), new AggregateId(userId));
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

    public UserPasswordChanged(final UUID userId) {
      super(new EventName(USER_PASSWORD_CHANGED), new AggregateId(userId));
    }
  }
}
