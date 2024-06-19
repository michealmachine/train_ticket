package com.demo12306.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Integer trainId;
    private Integer startId;
    private Integer endId;
    private Integer price;
}
