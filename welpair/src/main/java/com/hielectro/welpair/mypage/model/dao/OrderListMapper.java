package com.hielectro.welpair.mypage.model.dao;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;
import com.hielectro.welpair.order.model.dto.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderListMapper {

    List<OrderListDTO> selectOrderList(Map<String, Object> search);
    int orderListCount(Map<String, Object> search);
}
