package com.hielectro.welpair.inventory.model.dto;

import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
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

    private CategoryDTO category;
    private String categoryCode;

    private ProductDTO product;
    private int productAmount;

    private Date startDate;
    private Date endDate;

    private ProductOrderDTO productOrder;
}
