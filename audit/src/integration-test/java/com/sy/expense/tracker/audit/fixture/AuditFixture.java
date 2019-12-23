package com.sy.expense.tracker.audit.fixture;

import com.sy.expense.tracker.audit.domain.Audit;
import com.sy.expense.tracker.audit.domain.AuditId;
import java.util.UUID;

/**
 * Return prepared instance of {@link Audit} to use during testing.
 */
public final class AuditFixture {

  private AuditFixture() {}

  public static final Audit firstDatabaseAuditRecord() {

    return new Audit(
        new AuditId(UUID.fromString("cc654fe9-c239-4272-a002-8096552aec5f")),
        "EXPENSE_ADDED"
    );
  }

  public static final Audit secondDatabaseAuditRecord() {

    return new Audit(
        new AuditId(UUID.fromString("dd654fe9-c399-5383-a008-9097663aec5f")),
        "USER_REGISTERED"
    );
  }

  public static final Audit anAuditRecordToCreate() {

    return new Audit(
        new AuditId(UUID.fromString("ee777ff9-e899-6666-e668-9997663eee5e")),
        "EXPENSE_ADDED"
    );
  }
}
