package com.sy.expense.tracker.audit.application;

import com.sy.expense.tracker.audit.domain.Audit;
import com.sy.expense.tracker.audit.domain.AuditRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class AuditApplicationService {

  private final AuditRepository auditRepository;

  public List<Audit> getAudit() {

    return auditRepository.findAll();
  }

}
