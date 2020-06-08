package com.epam.izh.rd.online.autcion.entity;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Ставка
 */

public class Bid {
    private Long bidId;
    private LocalDate bidDate;
    private Double bidValue;
    private Long itemId;
    private Long userId;

    public Bid(Long bidId, LocalDate bidDate, Double bidValue, Long itemId, Long userId) {
        this.bidId = bidId;
        this.bidDate = bidDate;
        this.bidValue = bidValue;
        this.itemId = itemId;
        this.userId = userId;
    }

    public Bid() {
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public LocalDate getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDate bidDate) {
        this.bidDate = bidDate;
    }

    public Double getBidValue() {
        return bidValue;
    }

    public void setBidValue(Double bidValue) {
        this.bidValue = bidValue;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
        Bid bid = (Bid) o;
        return Objects.equals(bidId, bid.bidId) &&
                Objects.equals(bidDate, bid.bidDate) &&
                Objects.equals(bidValue, bid.bidValue) &&
                Objects.equals(itemId, bid.itemId) &&
                Objects.equals(userId, bid.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bidId, bidDate, bidValue, itemId, userId);
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", bidDate=" + bidDate +
                ", bidValue=" + bidValue +
                ", itemId=" + itemId +
                ", userId=" + userId +
                '}';
    }
}
