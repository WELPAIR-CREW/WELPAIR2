package com.hielectro.welpair.sellproduct.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellProductDetailDTO {

    private String id;
    private String pageNo;
    private String name;
    private String status;
    private int price;
    private float discount;
    private String option;
    private String title;
    private String sellStatus;
    private String thumbnailName;
}
