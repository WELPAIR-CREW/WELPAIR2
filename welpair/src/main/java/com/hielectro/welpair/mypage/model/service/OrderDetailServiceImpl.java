package com.hielectro.welpair.mypage.model.service;


import com.hielectro.welpair.mypage.model.dao.OrderDetailMapper;
import com.hielectro.welpair.mypage.model.dto.OrderDetailDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailMapper orderDetailMapper;

    public OrderDetailServiceImpl(OrderDetailMapper orderDetailMapper) {
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public List<OrderDetailDTO> selectOrderDetail(String orderNo, String empNo) {
        return orderDetailMapper.selectOrderDetail(orderNo, empNo);
    }
}
