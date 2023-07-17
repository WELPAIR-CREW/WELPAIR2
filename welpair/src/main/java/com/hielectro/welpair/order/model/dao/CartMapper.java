package com.hielectro.welpair.order.model.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;

@Mapper
public interface CartMapper {

    public String selectCartNo();

    public int addCartSellProduct(CartSellProductDTO cartSellProduct);

    int makeCart(String empNo);

    CartDTO checkoutCartByMemberId(String empNo);

    int checkoutCartProductById(CartSellProductDTO cartSellProduct);

    SellProductDTO isSellProductById(String sellProductId);
    List<CartGeneralDTO> cartAllInfoSelect(String empNo);

    int cartAmountChange(CartSellProductDTO cartSellProduct);

    int deleteCartProduct(String product, String empNo);

    List<ThumbnailImageDTO> selectThumbnailImage(String no);
}
