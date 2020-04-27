package com.sy.expense.tracker.expense.api;

import com.sy.expense.tracker.expense.domain.ExpenseCategory;
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
@ApiModel(description = "Contain the user's expense")
public class UserExpense {

  @ApiModelProperty(
      value = "A string representing the user email address",
      example = "foo@bar.com")
  private String email;

  @ApiModelProperty(
      value = "A string representing the expense description",
      example = "A train tickets")
  private String description;

  @ApiModelProperty(
      value = "A string representing the expense category",
      example = "TRAVEL")
  private ExpenseCategory category;

  @ApiModelProperty(
      value = "A number representing the expense amount",
      example = "TRAVEL")
  private double amount;


}
