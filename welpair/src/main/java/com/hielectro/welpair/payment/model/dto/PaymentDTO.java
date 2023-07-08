package com.hielectro.welpair.payment.model.dto;

import lombok.*;
import org.springframework.core.annotation.Order;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {

    private String paymentNo;       // 결제승인번호
    private Timestamp paymentDate;  // 결제일시
    private int paymentPrice;       // 결제금액
    private String paymentType;     // 결제수단
    private String tid;             // 카카오페이 결제시 결제고유번호

//    private OrderPaymentDTO orderPayment;
}
