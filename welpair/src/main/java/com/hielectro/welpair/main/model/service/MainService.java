package com.hielectro.welpair.main.model.service;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;

public interface MainService {
    SellProductDTO selectOneSellProduct(String pageNo);
}
