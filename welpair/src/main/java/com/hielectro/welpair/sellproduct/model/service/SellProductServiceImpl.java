package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;
import java.util.Map;

import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import org.springframework.stereotype.Service;

import com.hielectro.welpair.sellproduct.model.dao.SellProductMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

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
    public List<SellProductDetailDTO> selectProductList(Map<String, String> productId) {
        return productMapper.selectProductList(productId);
    }

    @Override
    public List<ReviewManagerDTO> selectReviewList() {
        return productMapper.selectReviewList();
    }

    @Override
    public int delete(List<String> request) throws Exception {
        int size = request.size();
        int result = 0;

        System.out.println("service : " + request);
        for (int i = 0; i < size; i++) {
            result += productMapper.delete(request.get(i));
        }

        if (result != size) {
            throw new IllegalStateException();
        }

        return result;
    }
}
