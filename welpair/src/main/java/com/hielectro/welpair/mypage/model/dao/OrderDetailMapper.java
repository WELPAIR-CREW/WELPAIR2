package com.hielectro.welpair.mypage.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.mypage.model.dto.OrderDetailDTO;

@Mapper
public interface OrderDetailMapper {

    List<OrderDetailDTO> selectOrderDetail(String orderNo, String empNo);
}
