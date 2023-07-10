package com.hielectro.welpair.delivery.model.dto;

import com.hielectro.welpair.order.model.dto.OrderDTO;
import lombok.*;

import java.sql.Timestamp;

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
