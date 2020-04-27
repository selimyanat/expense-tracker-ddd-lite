package com.sy.expense.tracker.identity.api;

/**
 * Return prepared instance of {@link CreateUserRequest} to use during testing.
 */
public final class CreateUserRequestFixture {

  private CreateUserRequestFixture() {}

  public static final CreateUserRequest createUser1Request() {

    return new CreateUserRequest()
        .setUserName("user1")
        .setPassword("password")
        .setEmail("user1@earth.com");
  }

}
