package com.hielectro.welpair.sales.model.dao;

import com.hielectro.welpair.sales.model.dto.SalesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SalesDAO {

    List<SalesDTO> getMonthSales();
}
