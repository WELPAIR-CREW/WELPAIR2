package com.hielectro.welpair.delivery.model.service;

import com.hielectro.welpair.delivery.model.dao.deliveryDAO;
import com.hielectro.welpair.delivery.model.dto.DriverDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class deliveryService {

    private final deliveryDAO delivery;

    public deliveryService(deliveryDAO delivery) {
        this.delivery = delivery;
    }

//    DAO에 만들어진 메소드 호출, 비지니스 로직이 필요하다면 작성
    public List<DriverDTO> deliveryDriver() {
        return delivery.deliveryDriver();


    }
}
