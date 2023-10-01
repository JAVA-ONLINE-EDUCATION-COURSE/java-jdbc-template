package com.epam.izh.rd.online.autcion.entity;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

/** Ставка */
@Getter
public class Bid {

  private Long bidId;
  private LocalDate bidDate;
  private Double bidValue;
  private Long itemId;
  private Long userId;

  public Bid() {}

  public Bid(long bidId, LocalDate bidDate, double bidValue, long itemId, long userId) {
    this.bidId = bidId;
    this.bidDate = bidDate;
    this.bidValue = bidValue;
    this.itemId = itemId;
    this.userId = userId;
  }

  public void setBidId(Long bidId) {
    this.bidId = bidId;
  }

  public void setBidDate(LocalDate bidDate) {
    this.bidDate = bidDate;
  }

  public void setBidValue(Double bidValue) {
    this.bidValue = bidValue;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Bid bid = (Bid) o;
    return Objects.equals(getBidId(), bid.getBidId())
        && Objects.equals(getBidDate(), bid.getBidDate())
        && Objects.equals(getBidValue(), bid.getBidValue())
        && Objects.equals(getItemId(), bid.getItemId())
        && Objects.equals(getUserId(), bid.getUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBidId(), getBidDate(), getBidValue(), getItemId(), getUserId());
  }

  @Override
  public String toString() {
    return "Bid{"
        + "bidId="
        + bidId
        + ", bidDate="
        + bidDate
        + ", bidValue="
        + bidValue
        + ", itemId="
        + itemId
        + ", userId="
        + userId
        + '}';
  }
}
