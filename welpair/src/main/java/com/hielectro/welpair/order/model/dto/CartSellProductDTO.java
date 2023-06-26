package com.hielectro.welpair.order.model.dto;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartSellProductDTO {

    private String cartNo;          // 카트번호
    private String sellProductId;   // 판매상품id
    private int cartAmount;         // 수량
    private int deliveryPrice;      // 배송비

    private SellProductDTO sellProduct; // 판매상품 dto
}
