package com.sy.expense.tracker.identity.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Integration test for class
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class IdentityEventProcessorIT {

//  @Autowired
//  IdentityEventProcessor identityEventProcessor;

  @Autowired
  ApplicationEventPublisher applicationEventPublisher;

  @Test
  public void when_a_userEvent_is_Fired_aNotification_isSent() {

//    var event = new UserEvent.UserRegistered(
//        new UserId(UUID.randomUUID()),
//        new Username("foo"),
//        new Email("foo@somewhere.com"));
//
//    applicationEventPublisher.publishEvent(event);
//    verify(identityEventProcessor.getNotificationApplicationService())
//        .notifyUserRegistration("foo", "foo@somewhere.com");
  }

//  @Configuration
//  @Import(IdentityEventProcessor.class)
//  static class TestContextConfiguration {
//
//    @Bean
//    NotificationApplicationService notificationApplicationService() {
//      return Mockito.mock(NotificationApplicationService.class);
//    }
//  }
}
