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
    private String categoryCode;

    private CategoryDTO category;
    private List<ProductDTO> productList;
    private List<StockDTO> StockList;

}
