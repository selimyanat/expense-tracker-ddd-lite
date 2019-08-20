package com.sy.expense.tracker.identityaccess.application;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationApplicationService {

  public void notifyUserRegistration(final String userName, final String userEmail) {
    LOG.info("User {} is successfully registered in the system... sending a welcome email to {}",
        userName, userEmail);
  }

}
