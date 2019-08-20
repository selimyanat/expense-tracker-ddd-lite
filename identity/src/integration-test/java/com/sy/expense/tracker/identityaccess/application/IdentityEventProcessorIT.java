package com.sy.expense.tracker.identityaccess.application;

import static org.mockito.Mockito.verify;

import com.sy.expense.tracker.identityaccess.domain.Email;
import com.sy.expense.tracker.identityaccess.domain.UserEvent;
import com.sy.expense.tracker.identityaccess.domain.UserId;
import com.sy.expense.tracker.identityaccess.domain.Username;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Integration test for class {@link IdentityEventProcessor}
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class IdentityEventProcessorIT {

  @Autowired
  IdentityEventProcessor identityEventProcessor;

  @Autowired
  ApplicationEventPublisher applicationEventPublisher;

  @Test
  public void when_a_userEvent_is_Fired_aNotification_isSent() {

    var event = new UserEvent.UserRegistered(
        new UserId(UUID.randomUUID()),
        new Username("foo"),
        new Email("foo@somewhere.com"));

    applicationEventPublisher.publishEvent(event);
    verify(identityEventProcessor.getNotificationApplicationService())
        .notifyUserRegistration("foo", "foo@somewhere.com");
  }

  @Configuration
  @Import(IdentityEventProcessor.class)
  static class TestContextConfiguration {

    @Bean
    NotificationApplicationService notificationApplicationService() {
      return Mockito.mock(NotificationApplicationService.class);
    }
  }
}
