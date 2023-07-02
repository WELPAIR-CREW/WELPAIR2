package com.hielectro.welpair.sales.model.dto;

import com.hielectro.welpair.inventory.model.dto.CategoryDTO;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import com.hielectro.welpair.payment.dto.OrderPaymentDTO;
import com.hielectro.welpair.payment.dto.PaymentDTO;
import lombok.*;

import java.text.DecimalFormat;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalesDTO {

    private PaymentDTO payment; // 주문번호
    private OrderPaymentDTO orderPayment; // 주문번호 + 판매상품ID
    private ProductOrderDTO productOrder; // 판매상품ID + 상품코드
    private ProductDTO product; // 상품코드 + 카테고리코드
    private CategoryDTO category; // 카테고리코드 + 카테고리명

    private int month;
    private int totalSales;


//    public String getPaymentType() {
//        if (payment != null && payment.getPaymentType() != null) {
//            return payment.getPaymentType();
//        }
//        return null;
//    }
//
//    public String getCategoryCode() {
//        if (category != null && category.getCategoryCode() != null) {
//            return category.getCategoryCode();
//        }
//        return null;
//    }
}
