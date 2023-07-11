package com.hielectro.welpair.main.model.dao;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {
    SellProductDTO selectOneSellProduct(String pageNo);
    List<SellProductDetailDTO> selectProductList();
}
