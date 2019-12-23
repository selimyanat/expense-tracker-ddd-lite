package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import java.util.List;

public interface OutboxStore {

  void append(StoredEvent aStoredEvent);

  List<StoredEvent> allStoredEventSince(long aStoredEventId);

  long getLastStoredEventId();

}
