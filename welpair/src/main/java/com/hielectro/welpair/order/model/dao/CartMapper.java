package com.hielectro.welpair.order.model.dao;


import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

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
}
