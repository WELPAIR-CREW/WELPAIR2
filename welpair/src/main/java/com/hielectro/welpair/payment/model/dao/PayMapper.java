package com.hielectro.welpair.payment.model.dao;

import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import com.hielectro.welpair.payment.model.dto.CartPayReqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {

    CartGeneralDTO selectProductById(String sellProductId);

}
