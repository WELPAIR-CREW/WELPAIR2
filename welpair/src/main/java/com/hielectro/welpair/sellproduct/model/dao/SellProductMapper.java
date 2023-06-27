package com.hielectro.welpair.sellproduct.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

@Mapper
public interface SellProductMapper {
    List<SellProductDTO> findSellProductByPageNo(int pageNo);
    int sellProductTotalCount();
    List<SellProductDTO> findSellProductByCode(Map<String, String> sellProductCode);

    int delete(String id);
}
