package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hielectro.welpair.sellproduct.model.dao.SellProductMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

@Service
public class SellProductServiceImpl implements SellProductService {
    private final SellProductMapper productMapper;

    public SellProductServiceImpl(SellProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<SellProductDTO> findSellProductByPageNo(int pageNo) {
        return productMapper.findSellProductByPageNo(pageNo);
    }

    public int sellProductTotalCount() {
        return productMapper.sellProductTotalCount();
    }

    @Override
    public SellProductDTO findSellProductById(String productId) {
        return productMapper.findSellProductById(productId);
    }
}
