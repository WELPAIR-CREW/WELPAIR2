package com.hielectro.welpair.delivery.model.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DriverDTO {

    private int driverId;
    private String driverName;
    private String driverPhone;
  /*1. T_DELIVERY와 JOIN한다 했을 때
   * 한명의 기사는 여러가지의 주문을 배송할 수 있기 떄문에
   * List 형식으로 DeliveryDTO 변수를 가지고 있어야한다.
   */
    private List<DeliveryDTO> delivery;
}
