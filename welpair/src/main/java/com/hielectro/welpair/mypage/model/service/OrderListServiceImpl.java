package com.hielectro.welpair.mypage.model.service;


import com.hielectro.welpair.mypage.model.dao.OrderListMapper;
import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderListServiceImpl implements OrderListService {


    private final OrderListMapper oerderListMapper;

    public OrderListServiceImpl(OrderListMapper oerderListMapper) {
        this.oerderListMapper = oerderListMapper;
    }


    @Override
    public List<OrderListDTO> selectOrderList(String empNo) {
        return oerderListMapper.selectOrderList(empNo);
    }
}
