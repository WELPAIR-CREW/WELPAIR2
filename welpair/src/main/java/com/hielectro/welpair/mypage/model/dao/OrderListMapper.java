package com.hielectro.welpair.mypage.model.dao;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderListMapper {

    List<OrderListDTO> selectOrderList(String empNo);
}
