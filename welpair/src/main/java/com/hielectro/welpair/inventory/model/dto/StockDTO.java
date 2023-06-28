package com.hielectro.welpair.inventory.model.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class StockDTO {

    private String stockNo;
    private String stockType;
    private String productCode;
    private Date stockDate;
    private int stockAmount;
    private String stockComment;
    private String categoryCode;

    private CategoryDTO category;
    private ProductDTO product;
}
