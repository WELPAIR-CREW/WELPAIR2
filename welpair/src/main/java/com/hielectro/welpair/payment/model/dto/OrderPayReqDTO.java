package com.hielectro.welpair.payment.model.dto;

import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayReqDTO {

    private List<ProductOrderDTO> orderPrdList;

    private int totalPaymentPrice;






}
