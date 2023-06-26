package com.hielectro.welpair.sellproduct.model.service;

import java.util.List;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

public interface SellProductService {
    public List<SellProductDTO> findSellProductByPageNo(int pageNo);

    public int sellProductTotalCount();
}
