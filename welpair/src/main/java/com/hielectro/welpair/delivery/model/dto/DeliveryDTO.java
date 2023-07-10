package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeliveryDTO {

    private String productOrderNo;
    private int driverId;
    private String deliveryStatus;

    private ProductByOrderDTO productByOrder;
    public List<adminOrderDTO> adminOrder;
}


