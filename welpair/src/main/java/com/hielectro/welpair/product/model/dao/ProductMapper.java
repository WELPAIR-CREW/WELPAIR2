package com.hielectro.welpair.product.model.dao;

import com.hielectro.welpair.product.model.dto.SellProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<SellProductDTO> findSellProductByPageNo(int pageNo);
    int sellProductTotalCount();
}
