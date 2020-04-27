package com.sy.expense.tracker.sharedkernel.infrastructure.events;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sy.expense.tracker.sharedkernel.domain.DomainEvent;
import lombok.SneakyThrows;

public class EventSerializer {

  private Gson gson;

  public EventSerializer() {
    this.gson = Converters.registerOffsetDateTime(new GsonBuilder()).create();
  }

  // TODO remove this, replace it by a try
  @SneakyThrows
  public String serialize(DomainEvent aDomainEvent) {

    return gson.toJson(aDomainEvent);
  }

  // TODO remove this, replace it by a try
  @SneakyThrows
  public <T extends DomainEvent> T deserialize(String aPayload, Class<T> aType) {

    return gson.fromJson(aPayload, aType);
  }
}
