package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hielectro.welpair.board.model.dto.QnAManagerDTO;
import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.sellproduct.model.dao.SellProductMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;

@Service
public class SellProductServiceImpl implements SellProductService {
    private final SellProductMapper productMapper;

    public SellProductServiceImpl(SellProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public int sellProductSearchCount(Map<String, String> search) {
        return productMapper.sellProductSearchCount(search);
    }

    @Override
    public int reviewSearchCount(Map<String, Object> searchMap) {
        return productMapper.reviewSearchCount(searchMap);
    }

    @Override
    public int qnaSearchCount(Map<String, Object> searchMap) {
        return productMapper.qnaSearchCount(searchMap);
    }


    @Override
    public List<SellProductDetailDTO> selectProductList(Map<String, String> productId) {
        return productMapper.selectProductList(productId);
    }

    @Override
    public List<ReviewManagerDTO> selectReviewList(Map<String, Object> searchMap) {
        return productMapper.selectReviewList(searchMap);
    }

    @Override
    public List<QnAManagerDTO> selectQnAList(Map<String, Object> searchMap) {
        return productMapper.selectQnAList(searchMap);
    }
    @Override
    public int sellProductDelete(List<String> request) throws IllegalStateException {
        int size = request.size();
        int result = 0;

        System.out.println("service : " + request);
        for (int i = 0; i < size; i++) {
            result += productMapper.sellProductDelete(request.get(i));
        }

        if (result != size) {
            throw new IllegalStateException();
        }

        return result;
    }


}
