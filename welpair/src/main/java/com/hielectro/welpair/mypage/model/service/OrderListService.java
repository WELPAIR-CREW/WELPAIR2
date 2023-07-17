package com.hielectro.welpair.mypage.model.service;

import java.util.List;
import java.util.Map;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;

public interface OrderListService {
    List<OrderListDTO> selectOrderList(Map<String, Object> search);
    int orderListCount(Map<String, Object> search);
}
