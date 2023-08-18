package com.zerobase.CafeBom.type;

public enum OrderCookingTime {

  _5_TO_10_MINUTES("5분 ~ 10분"),
  _10_TO_15_MINUTES("10분 ~ 15분"),
  _15_TO_20_MINUTES("5분 ~ 10분"),
  _20_TO_25_MINUTES("10분 ~ 15분"),
  OVER_25_MINUTES("25분 이상");

  private final String displayName;

  OrderCookingTime(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
