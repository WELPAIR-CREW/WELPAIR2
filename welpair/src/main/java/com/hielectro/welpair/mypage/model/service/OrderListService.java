package com.hielectro.welpair.mypage.model.service;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;

import java.util.List;
import java.util.Map;

public interface OrderListService {
    List<OrderListDTO> selectOrderList(Map<String, Object> search);
    int orderListCount(Map<String, Object> search);
}
