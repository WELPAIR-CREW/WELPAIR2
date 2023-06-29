package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeliveryDTO {

    private String productOrderNo;
    private int driverId;
    private String deliveryStatus;
}
