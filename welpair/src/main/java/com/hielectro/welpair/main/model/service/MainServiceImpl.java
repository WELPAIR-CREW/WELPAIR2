package com.hielectro.welpair.main.model.service;

import com.hielectro.welpair.main.model.dao.MainMapper;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<SellProductDetailDTO> selectProductList() {
        List<SellProductDetailDTO> result = mainMapper.selectProductList();
        final int SIZE = result.size();

        if (result.size() > 10) {
            Set<SellProductDetailDTO> list = new HashSet<>();

            while(list.size() != 10) {
                int rand = (int) (Math.random() * SIZE);
                list.add(result.get(rand));
            }

            return new ArrayList<>(list);
        }

        return result;
    }
}
