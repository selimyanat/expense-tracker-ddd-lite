package com.sy.expense.tracker.audit.api;

import static com.sy.expense.tracker.audit.api.AuditController.AUDIT_ROOT_URL;
import static com.sy.expense.tracker.audit.api.AuditController.GET_AUDIT_TRAIL_URL;
import static com.sy.expense.tracker.audit.fixture.AuditFixture.firstDatabaseAuditRecord;
import static com.sy.expense.tracker.audit.fixture.AuditFixture.secondDatabaseAuditRecord;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sy.expense.tracker.audit.application.AuditApplicationService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(secure = false)
@WebMvcTest(controllers = AuditController.class)
@WithMockUser
public class AuditControllerIT {

  @Autowired
  protected MockMvc mvc;

  @MockBean
  protected AuditApplicationService auditApplicationService;


  @Test
  public void getAuditTrail_returns_allAuditRecords_alongside_200() throws Exception {

    var audit1 = firstDatabaseAuditRecord();
    var audit2 = secondDatabaseAuditRecord();
    when(auditApplicationService.getAudit()).thenReturn(List.of(audit1, audit2));

    mvc.perform(get(AUDIT_ROOT_URL + GET_AUDIT_TRAIL_URL)
        .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(2)))
        // audit 1
        .andExpect(jsonPath("$[0].*", hasSize(2)))
        .andExpect(jsonPath("$[0].eventId", is(audit1.getId().getValue().toString())))
        .andExpect(jsonPath("$[0].eventName", is(audit1.getEventName())))
        // audit 2
        .andExpect(jsonPath("$[1].*", hasSize(2)))
        .andExpect(jsonPath("$[1].eventId", is(audit2.getId().getValue().toString())))
        .andExpect(jsonPath("$[1].eventName", is(audit2.getEventName())));
  }


}
