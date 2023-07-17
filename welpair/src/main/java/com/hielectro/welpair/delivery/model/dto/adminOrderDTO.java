package com.hielectro.welpair.delivery.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class adminOrderDTO {
//T_ORDER
    private String orderNo;
    private String orderDate;
    private int totalPrice;
    private String deliveryType;
    private String empNo;

}
