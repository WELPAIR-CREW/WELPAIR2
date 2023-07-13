package com.hielectro.welpair.order.model.dto;

import com.hielectro.welpair.delivery.model.dto.DeliveryDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.*;

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
    private int productOrderAmount; // 판매상품별 주문수량
    private int productOrderPrice;  // 판매상품별 주문금액
    private Date deliveryDate;      // 배송지정일

    // 판매상품 DTO
    private SellProductDTO Sellproduct; // 판매상품 dto
    private DeliveryDTO delivery;
}
