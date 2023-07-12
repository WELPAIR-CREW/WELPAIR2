package com.hielectro.welpair.mypage.model.dao;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    List<OrderDTO> selectOrderDetail(String orderNo, String empNo);
}
