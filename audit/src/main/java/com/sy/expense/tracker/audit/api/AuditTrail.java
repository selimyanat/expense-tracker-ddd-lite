package com.sy.expense.tracker.audit.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@ApiModel(description = "Contain the audit trail")
public class AuditTrail {

  @ApiModelProperty(
      value = "A string representing the event id",
      example = "23495585")
  private String eventId;

  @ApiModelProperty(
      value = "A string representing the event name",
      example = "USER_CREATION")
  private String eventName;

}
