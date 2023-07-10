package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductByOrderDTO {
    private String productOrderNo;
    private String orderNo;
    private String sellProductId;
    private int amount;
    private int orderPrice;
    private Date deliveryDate;  // 배송지정일

    private adminOrderDTO adminOrder;
//    PRODUCT_ORDER_NO
//            ORDER_NO
//    SELL_PRODUCT_ID
//            PRODUCT_ORDER_AMOUNT
//    PRODUCT_ORDER_PRICE
//            DELIVERY_DATE
}
