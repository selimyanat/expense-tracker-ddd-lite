package com.sy.expense.tracker.identity.domain;

import java.util.Optional;

/**
 * Repository backing {@link User}.
 *
 * @author selim
 */
public interface UserRepository {

  void insertUser(User user);

  Optional<User> findByUserId(UserId userId);

  Optional<User> findByUserEmail(Email userEmail);

}
