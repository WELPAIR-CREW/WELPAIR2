package com.hielectro.welpair.mypage.model.service;

import java.util.List;

import com.hielectro.welpair.mypage.model.dto.OrderDetailDTO;

public interface OrderDetailService {
    List<OrderDetailDTO> selectOrderDetail(String orderNo, String empNo);
}
