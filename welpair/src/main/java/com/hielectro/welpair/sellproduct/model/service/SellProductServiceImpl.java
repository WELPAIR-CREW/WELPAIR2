package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;
import java.util.Map;

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
    public List<SellProductDTO> findSellProductByCode(Map<String, String> productId) {
        return productMapper.findSellProductByCode(productId);
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
