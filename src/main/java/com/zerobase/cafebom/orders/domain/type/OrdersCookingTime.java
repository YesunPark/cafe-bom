package com.zerobase.cafebom.orders.domain.type;

public enum OrdersCookingTime {

  NONE("없음"),
  _5_TO_10_MINUTES("5분 ~ 10분"),
  _10_TO_15_MINUTES("10분 ~ 15분"),
  _15_TO_20_MINUTES("15분 ~ 20분"),
  _20_TO_25_MINUTES("20분 ~ 25분"),
  OVER_25_MINUTES("25분 이상");

  private final String displayName;

  OrdersCookingTime(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
