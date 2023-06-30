package com.hielectro.welpair.order.model.service;

import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

import java.util.List;

public interface CartService {
    List<CartGeneralDTO> cartAllInfoSelect(String empNo);

    public int makeCart(String empNo);

    public String selectCartNo();

    public int addCartSellProduct(CartSellProductDTO cartSellProduct);

    CartDTO checkoutCartByMemberId(String empNo);

    int checkoutCartProductById(CartSellProductDTO cartSellProduct);

    SellProductDTO isSellProductById(String sellProductId);

    boolean cartAmountChange(CartSellProductDTO changeAmount);
}
