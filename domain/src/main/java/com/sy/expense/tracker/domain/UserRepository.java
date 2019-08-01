package com.sy.expense.tracker.domain;

import java.util.Optional;

/**
 * Repository backing {@link User}
 */
public interface UserRepository {

  Optional<User> findById();

  void save(User user);

}
