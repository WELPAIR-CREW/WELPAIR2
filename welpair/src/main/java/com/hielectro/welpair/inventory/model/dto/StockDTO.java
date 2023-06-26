package com.hielectro.welpair.inventory.model.dto;

import lombok.*;

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
    private String stockDate;
    private int stockAmount;
    private String categoryCode;
    private ProductDTO productDTO;
}
