package com.hielectro.welpair.sales.model.dto;

import com.hielectro.welpair.payment.dto.PaymentDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalesDTO {

    private PaymentDTO payment;

    private int month;
    private int totalSales;

}
