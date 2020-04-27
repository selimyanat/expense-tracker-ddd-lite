package com.sy.expense.tracker.identity.api;

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
@ApiModel(description = "Represents the request to create a user")
public class CreateUserRequest {

  @ApiModelProperty(
      value = "A string representing the userName",
      example = "foobar")
  private String userName;

  @ApiModelProperty(
      value = "A string representing the user email address",
      example = "foo@bar.com")
  private String email;

  @ApiModelProperty(
      value = "A string representing the user password",
      example = "foobar")
  private String password;

}
