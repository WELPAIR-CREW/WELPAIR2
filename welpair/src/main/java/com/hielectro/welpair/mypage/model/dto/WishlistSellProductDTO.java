package com.hielectro.welpair.mypage.model.dto;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellItemPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class WishlistSellProductDTO {

    private String wishId;
    private String sellProductId;
    private String productName;
    private String price;
    private String thumbnail;

    //위시리스트별판매상품
    private SellProductDTO sellProductDTO; //판매상품dto
    private SellItemPageDTO sellItemPageDTO; //판매상품별페이지dto
    private SellPageDTO sellPageDTO; //판매상품페이지dto
    private ThumbnailImageDTO thumbnailImageDTO; //썸네일이미지dto (fk판매페이지번호를 가지고 있음)
    private ProductDTO productDTO; //판매상품dto

}
