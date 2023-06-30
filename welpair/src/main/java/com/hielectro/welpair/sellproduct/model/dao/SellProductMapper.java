package com.hielectro.welpair.sellproduct.model.dao;

import java.util.List;
import java.util.Map;

import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

@Mapper
public interface SellProductMapper {
    int sellProductSearchCount(Map<String, String> search);
    List<SellProductDetailDTO> selectProductList(Map<String, String> sellProductCode);
    List<ReviewManagerDTO> selectReviewList();
    int sellProductDelete(String id);
}
