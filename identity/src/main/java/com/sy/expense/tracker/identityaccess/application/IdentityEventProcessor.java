package com.sy.expense.tracker.identityaccess.application;

import com.sy.expense.tracker.identityaccess.domain.UserEvent.UserRegistered;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

/**
 * Event processor listening to user related events.
 */
@AllArgsConstructor
@Value
@Slf4j
public class IdentityEventProcessor {

  private final NotificationApplicationService notificationApplicationService;

  @EventListener
  public void onUserRegistered(UserRegistered userRegistered) {
    notificationApplicationService.notifyUserRegistration(
        userRegistered.getUsername().getValue(),
        userRegistered.getUserEmail().getValue());
  }
}
