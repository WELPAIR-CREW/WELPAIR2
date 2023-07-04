package com.hielectro.welpair.sales.model.dao;

import com.hielectro.welpair.sales.model.dto.SalesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SalesDAO {

//    List<SalesDTO> getMonthSales(String paymentType, String categoryCode);

    List<SalesDTO> getMonthSales(SalesDTO sales);

    void paymentType(@Param("paymentType")String paymentType);

    void categoryCode(@Param("categoryCode")String categoryCode);
}
