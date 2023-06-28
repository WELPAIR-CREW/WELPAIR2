package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;
import java.util.Map;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.springframework.transaction.annotation.Transactional;

public interface SellProductService {
    public List<SellProductDTO> findSellProductByPageNo(int pageNo);

    public int sellProductTotalCount();

    public List<SellProductDTO> findSellProductByCode(Map<String, String> productId);

    @Transactional(rollbackFor = {Exception.class})
    int delete(List<String> request) throws Exception;
}
