package com.epam.izh.rd.online.autcion.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Objects;

/** Лот в ставке */
@Getter
public class Item {

  @Id private Long itemId;
  private Double bidIncrement;
  private Boolean buyItNow;
  private String description;
  private LocalDate startDate;
  private Double startPrice;
  private LocalDate stopDate;
  private String title;
  private Long userId;

  public Item() {}

  public Item(
      long itemId,
      double bidIncrement,
      boolean buyItNow,
      String description,
      LocalDate startDate,
      double startPrice,
      LocalDate stopDate,
      String title,
      long userId) {
    this.itemId = itemId;
    this.bidIncrement = bidIncrement;
    this.buyItNow = buyItNow;
    this.description = description;
    this.startDate = startDate;
    this.startPrice = startPrice;
    this.stopDate = stopDate;
    this.title = title;
    this.userId = userId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public void setBidIncrement(Double bidIncrement) {
    this.bidIncrement = bidIncrement;
  }

  public void setBuyItNow(Boolean buyItNow) {
    this.buyItNow = buyItNow;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public void setStartPrice(Double startPrice) {
    this.startPrice = startPrice;
  }

  public void setStopDate(LocalDate stopDate) {
    this.stopDate = stopDate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Item item = (Item) o;
    return Objects.equals(getItemId(), item.getItemId())
        && Objects.equals(getBidIncrement(), item.getBidIncrement())
        && Objects.equals(getBuyItNow(), item.getBuyItNow())
        && Objects.equals(getDescription(), item.getDescription())
        && Objects.equals(getStartDate(), item.getStartDate())
        && Objects.equals(getStartPrice(), item.getStartPrice())
        && Objects.equals(getStopDate(), item.getStopDate())
        && Objects.equals(getTitle(), item.getTitle())
        && Objects.equals(getUserId(), item.getUserId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getItemId(),
        getBidIncrement(),
        getBuyItNow(),
        getDescription(),
        getStartDate(),
        getStartPrice(),
        getStopDate(),
        getTitle(),
        getUserId());
  }

  @Override
  public String toString() {
    return "Item{"
        + "itemId="
        + itemId
        + ", bidIncrement="
        + bidIncrement
        + ", buyItNow="
        + buyItNow
        + ", description='"
        + description
        + '\''
        + ", startDate="
        + startDate
        + ", startPrice="
        + startPrice
        + ", stopDate="
        + stopDate
        + ", title='"
        + title
        + '\''
        + ", userId="
        + userId
        + '}';
  }
}
