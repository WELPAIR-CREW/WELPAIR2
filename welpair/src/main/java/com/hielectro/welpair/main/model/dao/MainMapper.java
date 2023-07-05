package com.hielectro.welpair.main.model.dao;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {
    SellProductDTO selectOneSellProduct(String pageNo);
}
