package com.sy.expense.tracker.identity.sortit;

/**
 * Represents an event bus that publish domain events to all interested parties.
 *
 * @author selim
 */
public interface EventBus {

  /**
   * Publish a domain event to all interested parties.
   * @param domainEvent the event to publish
   */
  void publish(String domainEvent);

}
