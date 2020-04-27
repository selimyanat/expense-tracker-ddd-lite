package com.sy.expense.tracker.audit.api;


import static com.sy.expense.tracker.audit.api.AuditController.AUDIT_ROOT_URL;

import com.sy.expense.tracker.audit.application.AuditApplicationService;
import com.sy.expense.tracker.audit.domain.Audit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Api(tags = "Audit")
@RestController
@RequestMapping(path = AUDIT_ROOT_URL)
public class AuditController {

  public static final String AUDIT_ROOT_URL = "/audit";

  public static final String GET_AUDIT_TRAIL_URL = "";

  private final AuditApplicationService auditApplicationService;

  @ApiOperation(value = "Returns audit trail")
  @GetMapping(GET_AUDIT_TRAIL_URL)
  public List<AuditTrail> getAuditTrail() {

    return auditApplicationService.getAudit()
        .stream()
        .map(expense -> mapToAuditTrail(expense))
        .collect(Collectors.toList());
  }

  private AuditTrail mapToAuditTrail(Audit expense) {

    return new AuditTrail()
        .setEventId(expense.getId().getValue().toString())
        .setEventName(expense.getEventName());
  }

}
