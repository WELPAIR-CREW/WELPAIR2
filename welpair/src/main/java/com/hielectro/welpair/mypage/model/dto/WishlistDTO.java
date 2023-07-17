package com.hielectro.welpair.mypage.model.dto;

import java.util.Date;
import java.util.List;

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
public class WishlistDTO {

    private String wishId;
    private String empNo;
    private Date wishDate;

    //위시리스트에 담긴 판매상품아이디
    private List<WishlistSellProductDTO> wishlistSellProductList;

}
