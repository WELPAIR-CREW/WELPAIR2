package com.hielectro.welpair.order.model.service;

import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.order.model.dao.OrderMapper;
import com.hielectro.welpair.order.model.dto.CartSellProductDTO;
import com.hielectro.welpair.sellproduct.model.dao.SellProductMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final SellProductMapper sellProductMapper;

    public OrderServiceImpl(OrderMapper orderMapper, SellProductMapper sellProductMapper) {
        this.orderMapper = orderMapper;
        this.sellProductMapper = sellProductMapper;
    }

    @Override
    public List<SellProductDTO> findSellProductByCode(String productId) {
        return sellProductMapper.findSellProductByCode(productId);
    }

    @Transactional
    @Override
    public int addcart(String empNo) {
        return orderMapper.addcart(empNo);
    }

    @Override
    public String selectCartNo() {
        return orderMapper.selectCartNo();
    }

    @Transactional
    @Override
    public int addCartSellProduct(CartSellProductDTO cartSellProduct) {
        return orderMapper.addCartSellProduct(cartSellProduct);
    }

    //
    @Override
    public MemberDTO checkoutCartByMemberId(String empNo) {
        return null;
    }
}
