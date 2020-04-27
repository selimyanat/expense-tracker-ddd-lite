package com.sy.expense.tracker.audit.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents an audit records with its core attributes.
 *
 * @author selim
 */
@EqualsAndHashCode(of = "id")
@Getter
public class Audit {

  private AuditId id;

  private String eventName;

  public Audit(AuditId auditId, String eventName) {

    checkArgument(nonNull(auditId), "audit id cannot be null");
    checkArgument(isNotEmpty(eventName), "audit event name cannot be null or empty");

    this.id = auditId;
    this.eventName = eventName;
  }


}
