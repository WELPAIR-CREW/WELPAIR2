package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;
import java.util.Map;

import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.springframework.transaction.annotation.Transactional;

import com.hielectro.welpair.board.model.dto.QnAManagerDTO;
import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;

public interface SellProductService {
    // SELECT
    int sellProductSearchCount(Map<String, String> search);
    List<SellProductDetailDTO> selectProductList(Map<String, String> productId);
    SellProductDTO selectOneSellProduct(String pageNo);
    List<ReviewManagerDTO> selectReviewList(Map<String, Object> searchMap);
    int reviewSearchCount(Map<String, Object> searchMap);
    List<QnAManagerDTO> selectQnAList(Map<String, Object> searchMap);
    int qnaSearchCount(Map<String, Object> searchMap);
    List<ProductDTO> selectOptionList(ProductDTO product);
    List<ProductDTO> selectProductNameList(ProductDTO product);

    // INSERT
    void insertSellProduct(SellProductDTO sellProduct);

    // DELETE
    int sellProductDelete(List<String> request) throws Exception;
}
