package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class adminOrderDTO {

    private String orderNo;
    private String orderDate;
    private int totalPrice;
    private String deliveryType;
    private String empNo;

}
