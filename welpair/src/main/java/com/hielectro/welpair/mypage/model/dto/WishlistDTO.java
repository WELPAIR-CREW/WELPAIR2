package com.hielectro.welpair.mypage.model.dto;

import com.hielectro.welpair.sellproduct.model.dto.SellItemPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

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
