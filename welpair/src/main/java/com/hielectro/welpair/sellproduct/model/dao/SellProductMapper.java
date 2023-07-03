package com.hielectro.welpair.sellproduct.model.dao;

import java.util.List;
import java.util.Map;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import com.hielectro.welpair.board.model.dto.QnAManagerDTO;
import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;

@Mapper
public interface SellProductMapper {
    int sellProductSearchCount(Map<String, String> search);
    List<SellProductDetailDTO> selectProductList(Map<String, String> sellProductCode);
    List<ReviewManagerDTO> selectReviewList(Map<String, Object> searchMap);
    int sellProductDelete(String id);
    int reviewSearchCount(Map<String, Object> searchMap);
    int qnaSearchCount(Map<String, Object> searchMap);
    List<QnAManagerDTO> selectQnAList(Map<String, Object> searchMap);
    List<ProductDTO> selectOptionList(ProductDTO product);
}
