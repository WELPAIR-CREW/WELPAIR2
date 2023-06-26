package com.hielectro.welpair.inventory.model.dto;

import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO {

    private String productCode;
    private String productName;
    private String productStatus;
    private int productAmount;
    private int productPrice;
    private String productOption;
    private String productNation;
    private CategoryDTO categoryCode;

    private List<StockDTO> StockList;

}
