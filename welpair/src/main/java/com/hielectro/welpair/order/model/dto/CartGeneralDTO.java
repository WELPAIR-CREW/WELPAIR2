package com.hielectro.welpair.order.model.dto;


import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellItemPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartGeneralDTO {

    private CartDTO cart;
    private CartSellProductDTO cartSellProduct;
    private SellProductDTO sellProduct ;
    private SellItemPageDTO sellItemPage;
    private SellPageDTO sellPage;


    private ThumbnailImageDTO thumbImage;
    private ProductDTO product;

    private int price;
    private int totalPrice;


    private int exptPrice;
    private int exptDeliveryPrice;
    private int exptTotalPrice;
}

