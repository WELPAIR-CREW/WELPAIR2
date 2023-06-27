package com.hielectro.welpair.order.model.dao;


import com.hielectro.welpair.order.model.dto.CartDTO;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    public SellProductDTO checkoutSellProductId(String sellProductId);

    public String selectCartNo();

    public int addCartSellProduct(CartSellProductDTO cartSellProduct);

    int makeCart(String empNo);

    CartDTO checkoutCartByMemberId(String empNo);
}
