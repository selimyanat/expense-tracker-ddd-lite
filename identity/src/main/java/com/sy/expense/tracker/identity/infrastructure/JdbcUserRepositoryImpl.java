package com.sy.expense.tracker.identity.infrastructure;

import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.EncryptedPassword;
import com.sy.expense.tracker.identity.domain.User;
import com.sy.expense.tracker.identity.domain.UserId;
import com.sy.expense.tracker.identity.domain.UserRepository;
import io.vavr.API;
import io.vavr.API.Match.Case0;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

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
   *
   * @param identityJdbcTemplate the jdbc template to interact with the database
   */
  public JdbcUserRepositoryImpl(JdbcTemplate identityJdbcTemplate) {

    this.jdbcTemplate = identityJdbcTemplate;
    this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
    this.simpleJdbcInsert
        .withTableName("users")
        .usingGeneratedKeyColumns("user_id");
    this.userRowMapper = new UserRowMapper();
  }

  @Override
  public void insertUser(User user) {

    final Map<String, Object> parameters = new HashMap<>(5);
    parameters.put("user_uuid", user.getId().getValue());
    parameters.put("user_name", user.getUsername());
    parameters.put("encrypted_password", user.getEncryptedPassword().getValue());
    parameters.put("email", user.getEmail().getValue());

    var id = this.simpleJdbcInsert.executeAndReturnKey(parameters);
    LOG.debug("Newly user {} created in database with ID {} and UUID {}",
        user.getUsername(), id, user.getId().getValue());
  }

  @Override
  public Optional<User> findByUserId(UserId userId) {

    var response = Try.of(() -> this.jdbcTemplate
        .queryForObject("select * from users aUser WHERE aUser.user_uuid = ?",
            userRowMapper, userId.getValue()))
        .map(user -> Optional.of(user))
        .onFailure(throwable -> {
          var isEmptyResultException = throwable instanceof EmptyResultDataAccessException;
          if (!isEmptyResultException) {
            LOG.error("Fail to read user with id {} from database", userId.getValue(), throwable);
          }
        });

    return response.getOrElse(Optional.empty());
  }

  @Override
  public Optional<User> findByUserEmail(Email userEmail) {

    var response = Try.of(() -> this.jdbcTemplate.queryForObject("select * from users aUser "
        + " where aUser.email = ?", userRowMapper, userEmail.getValue()))
        .map(user -> Optional.of(user))
        .onFailure(throwable -> {
          var isEmptyResultException = throwable instanceof EmptyResultDataAccessException;
          if (!isEmptyResultException) {
            LOG.warn("Fail to read user with email {} from database", userEmail.getValue(),
                throwable);
          }
        });

    return response.getOrElse(Optional.empty());
  }

  private static class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

      var user = new User(
          new UserId(UUID.fromString(rs.getString("user_uuid"))),
          rs.getString("user_name"),
          new EncryptedPassword(rs.getString("encrypted_password")),
          new Email(rs.getString("email"))
      );
      return user;
    }
  }
}
