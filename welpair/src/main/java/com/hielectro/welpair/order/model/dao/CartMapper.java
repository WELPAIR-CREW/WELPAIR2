package com.hielectro.welpair.order.model.dao;


import com.hielectro.welpair.order.model.dto.CartGeneralDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    List<CartGeneralDTO> cartAllInfoSelect(String empNo);

    int testCartAllInfoSelect(String empNo);
}
