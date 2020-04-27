package com.sy.expense.tracker.audit.domain;

import java.util.List;

public interface AuditRepository {

  List<Audit> findAll();

  void insertAudit(Audit audit);

}
