package com.hielectro.welpair.order.model.dto;

import lombok.*;

import javax.validation.constraints.Min;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductOrderDTO {

    private String productOrderNo;  // 판매상품별주문번호(배송번호)
    private String orderNo;         // 주문번호
    private String sellProductId;   // 판매상품id
    @Min(1)
    private int productOrderAmount; // 판매상품별 주문수량
    @Min(1)
    private int productOrderPrice;  // 판매상품별 주문금액
    private Date deliveryDate;      // 배송지정일

    // 판매상품 DTO
    private SellProductDTO Sellproduct; // 판매상품 dto
}
