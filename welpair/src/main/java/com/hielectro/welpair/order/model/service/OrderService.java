package com.hielectro.welpair.order.model.service;


import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

import java.util.List;

public interface OrderService {


    public List<SellProductDTO> findSellProductByCode(String productId);

    public int addcart(String empNo);

    public String selectCartNo();

    public int addCartSellProduct(CartSellProductDTO cartSellProduct);
}
