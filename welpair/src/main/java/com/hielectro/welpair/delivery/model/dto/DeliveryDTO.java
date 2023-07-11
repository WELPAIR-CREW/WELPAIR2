package com.hielectro.welpair.delivery.model.dto;

import com.hielectro.welpair.order.model.dto.OrderDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
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

    public List<OrderDTO> adminOrder;

    private List<SellProductDTO> sellProduct;

}
