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

//    private OrderPaymentDTO orderPayment;
}
//
//select *
//  from T_PAYMENT a
//  JOIN T_ORDER_PAYMENT b ON (a.PAYMENT_NO = b.PAYMENT_NO)
//
//paymentno, paymentDate, paymentPrice, paymentType, orderNo, paymentNo