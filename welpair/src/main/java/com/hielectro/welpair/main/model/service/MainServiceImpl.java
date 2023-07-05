package com.hielectro.welpair.main.model.service;

import com.hielectro.welpair.main.model.dao.MainMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {
    private final MainMapper mainMapper;

    public MainServiceImpl(MainMapper mainMapper) {
        this.mainMapper = mainMapper;
    }

    @Override
    public SellProductDTO selectOneSellProduct(String pageNo) {
        return mainMapper.selectOneSellProduct(pageNo);
    }
}
