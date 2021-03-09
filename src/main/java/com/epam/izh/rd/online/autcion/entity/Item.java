package com.epam.izh.rd.online.autcion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/**
 * Лот в ставке
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private Long itemId;
    private Double bidIncrement;
    private Boolean buyItNow;
    private String description;
    private LocalDate startDate;
    private Double startPrice;
    private LocalDate stopDate;
    private String title;
    private Long userId;
}
