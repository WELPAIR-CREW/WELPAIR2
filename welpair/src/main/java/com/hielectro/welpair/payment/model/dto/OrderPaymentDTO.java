package com.hielectro.welpair.payment.model.dto;

import com.hielectro.welpair.order.model.dto.OrderDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderPaymentDTO {

    private String orderNo;     // 주문번호
    private String paymentNo;   // 결제승인번호

    private OrderDTO order;     // 주문번호 dto
    private PaymentDTO payment; // 결제 dto



}
