package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotDeliveryDTO {
        private String productOrderNo;
        private String sellProductId;
        private int productOrderAmount;
        private Date deliveryDate;
        private Date orderDate;
        private String addressName;
        private String productName;
        private String deliveryStatus;

        private String updateStatus;

}
