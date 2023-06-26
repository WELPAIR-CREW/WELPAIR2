package com.hielectro.welpair.sellproduct.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

@Mapper
public interface SellProductMapper {
    List<SellProductDTO> findSellProductByPageNo(int pageNo);
    int sellProductTotalCount();
    SellProductDTO findSellProductById(String productId);
}
