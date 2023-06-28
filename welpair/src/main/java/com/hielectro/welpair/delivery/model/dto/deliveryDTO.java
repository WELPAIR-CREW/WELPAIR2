package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class deliveryDTO {

    private int productOrderNumber;
    private String driverId;
    private String deliveryStatus;
}
