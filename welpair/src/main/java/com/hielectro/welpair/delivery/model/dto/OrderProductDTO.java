package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderProductDTO {
    private Date orderDate;
    private String productOrderNo;  // 배송번호
    private String productName;
    private int amount;
    private String recipient;
    private Date deliveryDate;
    private String deliveryStatus;
    private String driverId;
    private String driverName;
    private String addressName;
}
