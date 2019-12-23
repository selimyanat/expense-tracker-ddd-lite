package com.sy.expense.tracker.identity.infrastructure;

import static com.sy.expense.tracker.identity.fixture.UserFixture.aUserToCreate;
import static com.sy.expense.tracker.identity.fixture.UserFixture.firstDatabaseUser;
import static org.assertj.core.api.Assertions.assertThat;

import com.sy.expense.tracker.identity.domain.Email;
import com.sy.expense.tracker.identity.domain.UserId;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Integration test for {@link JdbcUserRepositoryImpl}.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@JdbcTest
@Sql(
    statements = "insert into users (user_uuid, user_name, email, encrypted_password) "
        + "values ("
        + "'ff654fe9-c349-4370-a472-8096552aec5f', 'user1', 'user1@earth.com', 'erfn23fflfdf')")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:/identity.properties")
public class JdbcUserRepositoryIT {

  @Autowired
  @Qualifier("userRepository")
  private JdbcUserRepositoryImpl underTest;

  @Test
  public void findByUserId_when_user_doesNot_exist_returnsEmpty() {

    assertThat(underTest.findByUserId(new UserId(UUID.randomUUID())))
        .isEqualTo(Optional.empty());
  }

  @Test
  public void findByUserId_when_user_exists_returnsUser() {

    assertThat(underTest.findByUserId(new UserId(UUID.fromString("ff654fe9-c349-4370"
        + "-a472-8096552aec5f"))))
        .isNotEmpty()
        .contains(firstDatabaseUser());
  }

  @Test
  public void findByUserEmail_when_user_doesNot_exist_returnsEmpty() {

    assertThat(underTest.findByUserEmail(new Email("unknownUser@somewhere.com")))
        .isEmpty();
  }

  @Test
  public void findByUserEmail_when_user_exists_returnsUser() {

    assertThat(underTest.findByUserEmail(firstDatabaseUser().getEmail()))
        .isNotEmpty()
        .contains(firstDatabaseUser());
  }

  @Test
  public void insertUser_isOk() {

    var aUser = aUserToCreate();

    underTest.insertUser(aUser);
    assertThat(underTest
        .findByUserId(new UserId(UUID.fromString("cc774fe9-d888-4377-a475-9096552aec5c"))))
        .isNotEmpty()
        .contains(aUserToCreate());
  }

  @Configuration
  @Import({IdentityDatabaseConfig.class, JdbcUserRepositoryImpl.class})
  static class TestContextConfiguration {

  }

}
