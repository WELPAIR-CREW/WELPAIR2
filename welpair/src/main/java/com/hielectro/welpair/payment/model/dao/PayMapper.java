package com.hielectro.welpair.payment.model.dao;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.order.model.dto.ProductOrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {

    List<CartGeneralDTO> selectProductById(String sellProductId);

}
