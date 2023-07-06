package com.hielectro.welpair.common;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import lombok.*;

import java.util.ArrayList;



@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceCalculator {

    private int price;
    private float discount;
    private int deliveryPrice;
    private int totalPrice;
    private int amount;


    // 단품 금액 생성 메소드
    public int oneOfPrice(int price, float discount, int amount) {

        // 1품목 1개 가격
        return (int)((1.0 - discount) * price);
    }

    // 단품 금액 생성 메소드
    public int amountOfPrice(int price, float discount, int amount) {

        // 1품목 총수량 가격 (가격 * 수량)
        return (int)((1.0 - discount) * price * amount);
    }

    // 예상 총 결제금액 생성메소드(체크박스 선택시 바뀐다)
    public int totalPrice (int price, float discount, int amount, int deliveryPrice) {
        // 선택 총 합계
        totalPrice =  (int)((1.0 - discount) * price * amount) + deliveryPrice;
        return totalPrice;
    }
}