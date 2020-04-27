package com.sy.expense.tracker.sharedkernel.domain;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ADummyDomainEvent extends DomainEvent {

  static final String A_DUMMY_DOMAIN_EVENT = "aDummyDomainEvent";

  private String message;

  public ADummyDomainEvent(
      UUID anEventId,
      UUID anAggregateId,
      Instant anInstant,
      String aMessage) {

    super(
        new EventId(anEventId),
        new EventName(A_DUMMY_DOMAIN_EVENT),
        new AggregateId(anAggregateId), new EventTimestamp(anInstant));
    this.message = aMessage;
  }
}
