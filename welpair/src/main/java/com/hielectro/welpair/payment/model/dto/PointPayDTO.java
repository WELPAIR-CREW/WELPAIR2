package com.hielectro.welpair.payment.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PointPayDTO {

    private String pointNo;         // 포인트번호
    private String paymentNo;       // 결제승인번호

    private PaymentDTO payment;     //결제 dto



}
