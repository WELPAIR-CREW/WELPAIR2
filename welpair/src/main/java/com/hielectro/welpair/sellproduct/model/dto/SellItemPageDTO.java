package com.hielectro.welpair.sellproduct.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellItemPageDTO {
    private String no;  // Page Number
    private String id;  // Sell Product ID

    private SellPageDTO sellPage;
}
