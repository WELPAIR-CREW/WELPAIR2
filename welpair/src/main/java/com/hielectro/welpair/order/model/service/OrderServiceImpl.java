package com.hielectro.welpair.order.model.service;

import com.hielectro.welpair.order.model.dao.OrderMapper;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.order.model.dto.SellProductDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }



    @Override
    public SellProductDTO checkoutSellProductId(String sellProductId) {
        return orderMapper.checkoutSellProductId(sellProductId);
    }

    @Override
    public int addcart(String empNo) {
        return orderMapper.addcart(empNo);
    }

    @Override
    public String selectCartNo() {
        return orderMapper.selectCartNo();
    }

    @Override
    public int addCartSellProduct(CartSellProductDTO cartSellProduct) {
        return orderMapper.addCartSellProduct(cartSellProduct);
    }
}
