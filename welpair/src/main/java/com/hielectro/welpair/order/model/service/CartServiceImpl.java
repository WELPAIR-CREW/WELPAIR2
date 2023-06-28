package com.hielectro.welpair.order.model.service;

import com.hielectro.welpair.order.model.dao.CartMapper;
import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;

    public CartServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public List<CartGeneralDTO> cartAllInfoSelect(String empNo) {
        return cartMapper.cartAllInfoSelect(empNo);
    }

    @Override
    public int testCartAllInfoSelect(String empNo) {
        return cartMapper.testCartAllInfoSelect(empNo);
    }
}
