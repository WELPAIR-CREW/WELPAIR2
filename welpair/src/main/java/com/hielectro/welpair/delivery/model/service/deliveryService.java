package com.hielectro.welpair.delivery.model.service;

import com.hielectro.welpair.delivery.model.dao.deliveryMapper;
import com.hielectro.welpair.delivery.model.dto.DeliveryDTO;
import com.hielectro.welpair.delivery.model.dto.DriverDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class deliveryService {

    private final deliveryMapper delivery;

    public deliveryService(deliveryMapper delivery) {
        this.delivery = delivery;
    }

//    DAO에 만들어진 메소드 호출, 비지니스 로직이 필요하다면 작성
    public List<DeliveryDTO> deliveryDelivery() {
        return delivery.deliveryDelivery();


    }
}
