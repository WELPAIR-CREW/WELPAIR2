package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;
import java.util.Map;

import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import org.springframework.transaction.annotation.Transactional;

public interface SellProductService {
    public int sellProductSearchCount(Map<String, String> search);
    public List<SellProductDetailDTO> selectProductList(Map<String, String> productId);
    List<ReviewManagerDTO> selectReviewList();

    @Transactional(rollbackFor = {Exception.class})
    int delete(List<String> request) throws Exception;
}
