package com.hielectro.welpair.mypage.model.service;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDTO> selectOrderDetail(String orderNo, String empNo);
}
