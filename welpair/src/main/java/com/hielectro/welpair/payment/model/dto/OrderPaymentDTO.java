package com.hielectro.welpair.payment.model.dto;

import com.hielectro.welpair.order.model.dto.OrderDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderPaymentDTO {

    private String orderNo;     // 주문번호
    private String paymentNo;   // 결제승인번호

//    private OrderDTO order;     // 주문번호 dto
    private List<PaymentDTO> paymentList; // 결제 dto (1개 주문당 결제수단 1개이상)



}
