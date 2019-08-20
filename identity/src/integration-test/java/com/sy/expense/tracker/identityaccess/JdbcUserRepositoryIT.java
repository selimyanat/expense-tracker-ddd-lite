package com.sy.expense.tracker.identityaccess;

import static com.sy.expense.tracker.identityaccess.motherobject.UserMotherObject.aUserToCreate;
import static com.sy.expense.tracker.identityaccess.motherobject.UserMotherObject.firstDatabaseUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.sy.expense.tracker.identityaccess.domain.Email;
import com.sy.expense.tracker.identityaccess.domain.UserId;
import com.sy.expense.tracker.identityaccess.infrastructure.JdbcUserRepositoryImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@JdbcTest
@Sql(scripts = {"/database/schema/create_users.sql", "/resources/database/dataset/users.sql"})
public class JdbcUserRepositoryIT {

  @Autowired
  private JdbcUserRepositoryImpl jdbcUserRepository;

  @Test
  public void findByUserId_when_user_doesNot_exist_returnsEmpty() {

    assertThat(jdbcUserRepository.findByUserId(new UserId(UUID.randomUUID())))
        .isEqualTo(Optional.empty());
  }

  @Test
  public void findByUserId_when_user_exists_returnsUser() {

    var aUser = jdbcUserRepository.findByUserId(new UserId(UUID.fromString("ff654fe9-c349-4370"
        + "-a472-8096552aec5f")));
    assertThat(aUser.isPresent()).isTrue();
    assertThat(aUser.get()).isEqualTo(firstDatabaseUser());
  }

  @Test
  public void findByUserEmail_when_user_doesNot_exist_returnsEmpty() {

    assertThat(jdbcUserRepository.findByUserEmail(new Email("unknownUser@somewhere.com")))
        .isEqualTo(Optional.empty());
  }

  @Test
  public void findByUserEmail_when_user_exists_returnsUser() {

    var aUser = jdbcUserRepository.findByUserEmail(firstDatabaseUser().getEmail());
    assertThat(aUser.isPresent()).isTrue();
    assertThat(aUser.get()).isEqualTo(firstDatabaseUser());
  }

  @Test
  public void insertUser_isOk() {

    var aUser = aUserToCreate();

    jdbcUserRepository.insertUser(aUser);
    var expectedUser = jdbcUserRepository.findByUserId(new UserId(UUID.fromString("cc774fe9-d888-4377-a475-9096552aec5c")));
    assertThat(expectedUser.isPresent()).isTrue();
    assertThat(expectedUser.get()).isEqualTo(aUserToCreate());
  }

  @Configuration
  @Import(JdbcUserRepositoryImpl.class)
  static class TestContextConfiguration {

    private JdbcUserRepositoryImpl jdbcUserRepository(JdbcTemplate jdbcTemplate) {
      return new JdbcUserRepositoryImpl(jdbcTemplate);
    }
  }

 }
