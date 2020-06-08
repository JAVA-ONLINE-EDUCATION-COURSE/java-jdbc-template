package com.epam.izh.rd.online.autcion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Лот в ставке
 */

public class Item {
    private Long itemId;
    private Double bidIncrement;
    private Boolean buyItNow;
    private String description;
    private LocalDate startDate;
    private Double startPrice;
    private LocalDate stopDate;
    private String title;
    private Long userId;

    public Item(Long itemId, Double bidIncrement, Boolean buyItNow, String description, LocalDate startDate, Double startPrice, LocalDate stopDate, String title, Long userId) {
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

    public Item() {
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Double getBidIncrement() {
        return bidIncrement;
    }

    public void setBidIncrement(Double bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    public Boolean getBuyItNow() {
        return buyItNow;
    }

    public void setBuyItNow(Boolean buyItNow) {
        this.buyItNow = buyItNow;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId) &&
                Objects.equals(bidIncrement, item.bidIncrement) &&
                Objects.equals(buyItNow, item.buyItNow) &&
                Objects.equals(description, item.description) &&
                Objects.equals(startDate, item.startDate) &&
                Objects.equals(startPrice, item.startPrice) &&
                Objects.equals(stopDate, item.stopDate) &&
                Objects.equals(title, item.title) &&
                Objects.equals(userId, item.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, bidIncrement, buyItNow, description, startDate, startPrice, stopDate, title, userId);
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", bidIncrement=" + bidIncrement +
                ", buyItNow=" + buyItNow +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", startPrice=" + startPrice +
                ", stopDate=" + stopDate +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                '}';
    }
}
