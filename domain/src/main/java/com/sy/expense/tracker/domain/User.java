package com.sy.expense.tracker.domain;

import com.google.common.base.Preconditions;
import java.util.Objects;
import java.util.UUID;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Represents a user with its core attributes.
 */
public interface User {

  UserId getUserId();

  UserPasswordHash getUserPassword();

  UserName getUserName();

  UserEmail getUserEmail();

  /**
   * Value Object that represents a user identity.
   */
  @Value
  class UserId {

    private UUID value;

    UserId(final UUID value) {
      Preconditions.checkArgument(Objects.nonNull(value), "User id cannot be null");
      this.value = value;
    }
  }

  /**
   * Value Object that represents a user name.
   */
  @Value
  class UserName {

    private final String value;

    UserName(final String value) {
      Preconditions
          .checkArgument(StringUtils.isNotEmpty(value), "User name cannot be null or empty");
      this.value = value;
    }

  }
  /**
   * Value Object that represents a user password hash.
   */


  @Value
  class UserPasswordHash {
    private final String value;
    UserPasswordHash(final String value) {
      Preconditions.checkArgument(StringUtils.isNotEmpty(value), "User password hash cannot be "
          + "null or empty");
      this.value = value;
    }
  }

  /**
   * Value Object that represents a user email.
   */
  @Value
  class UserEmail {

    private final String value;

    UserEmail(final String value) {
      Preconditions
          .checkArgument(
              EmailValidator.getInstance().isValid(value),
              "User email %s is not a valid email", value);
      this.value = value;
    }
  }
}
