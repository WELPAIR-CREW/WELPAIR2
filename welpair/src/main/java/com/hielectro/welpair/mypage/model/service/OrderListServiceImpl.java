package com.hielectro.welpair.mypage.model.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hielectro.welpair.mypage.model.dao.OrderListMapper;
import com.hielectro.welpair.mypage.model.dto.OrderListDTO;

@Service
public class OrderListServiceImpl implements OrderListService {


    private final OrderListMapper orderListMapper;

    public OrderListServiceImpl(OrderListMapper orderListMapper) {
        this.orderListMapper = orderListMapper;
    }


    @Override
    public List<OrderListDTO> selectOrderList(Map<String, Object> search) {
        return orderListMapper.selectOrderList(search);
    }

    @Override
    public int orderListCount(Map<String, Object> search) {
        return orderListMapper.orderListCount(search);
    }
}
