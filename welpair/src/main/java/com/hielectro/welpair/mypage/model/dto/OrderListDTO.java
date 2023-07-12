package com.hielectro.welpair.mypage.model.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderListDTO {

    private Timestamp orderDate;    // 주문일시
    private String orderNo;         // 주문번호
    private String orderName;       // 주문명
    private int amount;             // 수량
    private int totalPrice;         // 주문금액
    private String deliveryStatus; // 세터 - null이면 배송준비중
    private String orderType;      // 주문유형

    public void setDeliveryStatus(String deliveryStatus) {
        if(deliveryStatus == null ){
            deliveryStatus = "배송준비중";
        }
        this.deliveryStatus = deliveryStatus;
    }

}
