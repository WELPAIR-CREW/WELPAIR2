package com.hielectro.welpair.sellproduct.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

import java.util.List;

@Mapper
public interface SellProductMapper {
    List<SellProductDTO> findSellProductByPageNo(int pageNo);
    int sellProductTotalCount();
}
