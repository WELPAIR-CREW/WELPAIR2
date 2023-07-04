package com.hielectro.welpair.sellproduct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
