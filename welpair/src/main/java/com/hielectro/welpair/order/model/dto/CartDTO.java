package com.hielectro.welpair.order.model.dto;


import com.hielectro.welpair.member.model.dto.MemberDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartDTO {

    private String cartNo;              // 카트번호
    private String empNo;               // 사번(회원번호)

    private MemberDTO member;          // 회원 dto
    private List<CartSellProductDTO> cartSellProductList;   // 판매상품 리스트
}
