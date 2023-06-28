package com.hielectro.welpair.sellproduct.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

@Mapper
public interface SellProductMapper {
    int sellProductSearchCount(Map<String, String> search);
    List<SellProductDTO> selectProductList(Map<String, String> sellProductCode);
    int delete(String id);
}
