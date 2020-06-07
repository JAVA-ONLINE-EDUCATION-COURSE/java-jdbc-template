package com.epam.izh.rd.online.autcion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Ставка
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    private Long bidId;
    private LocalDate bidDate;
    private Double bidValue;
    private Long itemId;
    private Long userId;
}
