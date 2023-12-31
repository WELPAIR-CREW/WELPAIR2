package com.hielectro.welpair.sales.model.dto;

import com.hielectro.welpair.inventory.model.dto.CategoryDTO;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.model.dto.OrderPaymentDTO;
import com.hielectro.welpair.payment.model.dto.PaymentDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalesDTO {

    private int month;
    private int totalSales;

    private PaymentDTO payment; // 주문번호
    private OrderPaymentDTO orderPayment; // 주문번호 + 판매상품ID
    private ProductOrderDTO productOrder; // 판매상품ID + 상품코드
    private SellProductDTO sellProduct; // 판매상품ID + 상품코드
    private ProductDTO product; // 상품코드 + 카테고리코드
    private CategoryDTO category; // 카테고리코드 + 카테고리명
}
