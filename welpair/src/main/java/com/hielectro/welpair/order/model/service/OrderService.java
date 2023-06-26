package com.hielectro.welpair.order.model.service;


import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

public interface OrderService {


    public SellProductDTO findSellProductById(String productId);

    public int addcart(String empNo);

    public String selectCartNo();

    public int addCartSellProduct(CartSellProductDTO cartSellProduct);
}
