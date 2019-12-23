package com.sy.expense.tracker.expense.domain;

import lombok.Getter;

public enum ExpenseCategory {

  RESTAURANTS(0),
  MORTGAGE(1),
  TAXI(2),
  ENTERTAINMENT(3),
  GAS(4),
  PHONE(5),
  TAXES(6),
  NEWSPAPER(7),
  GYM(8),
  SPORT(9),
  GIFT(10),
  HOTEL(11),
  TRAVEL(12),
  SHOPPING(13);

  @Getter
  private final int code;

  ExpenseCategory(int code) {

    this.code = code;
  }

  public static ExpenseCategory fromCode(int code) {
    return ExpenseCategory.values()[code];
  }
}
