package com.hielectro.welpair.delivery.model.dao;

import com.hielectro.welpair.delivery.model.dto.DriverDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface deliveryDAO {
    /* 3. deliveryDriver 쿼리를 실행하고 난 후의 결과값이
    *  하나가 아니기 때문에 List형으로 반환한다.
    * */
    List<DriverDTO> deliveryDriver();

    List<deliveryDAO> deliveryDelivery();

}