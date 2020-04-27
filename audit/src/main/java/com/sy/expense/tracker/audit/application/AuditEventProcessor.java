package com.sy.expense.tracker.audit.application;

import com.sy.expense.tracker.audit.domain.Audit;
import com.sy.expense.tracker.audit.domain.AuditId;
import com.sy.expense.tracker.audit.domain.AuditRepository;
import com.sy.expense.tracker.expense.domain.event.ExpenseEvent.ExpenseAdded;
import com.sy.expense.tracker.identity.domain.event.UserEvent.UserRegistered;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
@Slf4j
public class AuditEventProcessor {

  private final AuditRepository auditRepository;

  @EventListener
  public void onNewUserRegistered(UserRegistered userRegistered) {

    LOG.info("Saving {} in audit trail", userRegistered.getName().getValue());
    auditRepository.insertAudit(new Audit(
        new AuditId(userRegistered.getId().getValue()),
        userRegistered.getName().getValue())
    );
  }

  @EventListener
  public void onExpenseAdded(ExpenseAdded expenseAdded) {

    LOG.info("Saving {} in audit trail", expenseAdded.getName().getValue());
    auditRepository.insertAudit(new Audit(
        new AuditId(expenseAdded.getId().getValue()),
        expenseAdded.getName().getValue())
    );
  }

}
