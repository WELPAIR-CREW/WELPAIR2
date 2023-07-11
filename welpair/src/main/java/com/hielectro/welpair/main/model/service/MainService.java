package com.hielectro.welpair.main.model.service;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;

import java.util.List;

public interface MainService {
    SellProductDTO selectOneSellProduct(String pageNo);
    List<SellProductDetailDTO> selectProductList();
}
