package com.hielectro.welpair.mypage.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.mypage.model.dto.OrderListDTO;

@Mapper
public interface OrderListMapper {

    List<OrderListDTO> selectOrderList(Map<String, Object> search);
    int orderListCount(Map<String, Object> search);
}
