package com.hielectro.welpair.order.model.dto;


import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.payment.model.dto.OrderPaymentDTO;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {

    private String orderNo;         // 주문번호
    private Timestamp orderDate;    // 주문일시
    private int totalPrice;         // 총주문금액
    private String addressId;       // 배송지id
    private String deliveryType;    // 주문유형
    private String memberNo;        // 회원번호(사번)

    private MemberDTO member;   // member dto
    private List<OrderPaymentDTO> orderPaymentList; // 판매상품별주문 리스트

}
