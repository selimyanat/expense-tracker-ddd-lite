package com.sy.expense.tracker.identityaccess.infrastructure;

import com.sy.expense.tracker.identityaccess.domain.Email;
import com.sy.expense.tracker.identityaccess.domain.EncryptedPassword;
import com.sy.expense.tracker.identityaccess.domain.User;
import com.sy.expense.tracker.identityaccess.domain.UserId;
import com.sy.expense.tracker.identityaccess.domain.UserRepository;
import com.sy.expense.tracker.identityaccess.domain.Username;
import io.vavr.control.Try;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

/**
 * Jdbc repository implementation of {@link UserRepository}
 */
@Slf4j
public class JdbcUserRepositoryImpl implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  private final UserRowMapper userRowMapper;

  private SimpleJdbcInsert simpleJdbcInsert;


  /**
   * Create a new instance of <code>{@link JdbcUserRepositoryImpl}</code>
   * @param jdbcTemplate the jdbc template to interact with the database
   */
  public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate) {

    this.jdbcTemplate = jdbcTemplate;
    this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
    this.simpleJdbcInsert
        .withTableName("USERS")
        .usingGeneratedKeyColumns("USER_ID");
    this.userRowMapper = new UserRowMapper();
  }

  @Override
  @Transactional
  public void insertUser(User user) {

    final Map<String, Object> parameters = new HashMap<>(5);
    parameters.put("USER_UUID", user.getId().getValue());
    parameters.put("USER_NAME", user.getUsername().getValue());
    parameters.put("ENCRYPTED_PASSWORD", user.getEncryptedPassword().getValue());
    parameters.put("EMAIL", user.getEmail().getValue());

    var id = this.simpleJdbcInsert.executeAndReturnKey(parameters);
    LOG.debug("Newly user {} created in database with ID {} and UID {}",
        user.getUsername().getValue(), id, user.getId().getValue());
  }

  @Override
  public Optional<User> findByUserId(UserId userId) {

    var response = Try.of(() -> this.jdbcTemplate.queryForObject("SELECT * from USERS aUser where "
            + "aUser.USER_UUID = ?", userRowMapper, userId.getValue()))
        .map(user -> Optional.of(user))
        .onFailure(throwable -> LOG.warn("Fail to read user with id {} from database",
            userId.getValue(), throwable));

    return response.getOrElse(Optional.empty());
  }

  @Override
  public Optional<User> findByUserEmail(Email userEmail) {

    var response = Try.of(() -> this.jdbcTemplate.queryForObject("SELECT * from USERS aUser where "
        + "aUser.EMAIL = ?", userRowMapper, userEmail.getValue()))
        .map(user -> Optional.of(user))
        .onFailure(throwable -> LOG.warn("Fail to read user with email {} from database",
            userEmail.getValue(), throwable));

    return response.getOrElse(Optional.empty());
  }

  private static class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

      var user = new User(
          new UserId(UUID.fromString(rs.getString("USER_UUID"))),
          new Username(rs.getString("USER_NAME")),
          new EncryptedPassword(rs.getString("ENCRYPTED_PASSWORD")),
          new Email(rs.getString("EMAIL"))
      );
      return user;
    }
  }
}
