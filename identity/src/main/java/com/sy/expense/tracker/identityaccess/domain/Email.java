package com.sy.expense.tracker.identityaccess.domain;

import static com.google.common.base.Preconditions.checkArgument;

import lombok.Value;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Value Object that represents an email.
 */
@Value
public class Email {

  private final String value;

  public Email(final String value) {

    checkArgument(
        EmailValidator.getInstance().isValid(value),
        "User email %s is not a valid email", value);
    this.value = value;
  }

}
