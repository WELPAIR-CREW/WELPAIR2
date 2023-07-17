package com.hielectro.welpair.delivery.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hielectro.welpair.delivery.model.dao.deliveryMapper;
import com.hielectro.welpair.delivery.model.dto.NotDeliveryDTO;
import com.hielectro.welpair.delivery.model.dto.OrderProductDTO;

@Service
public class deliveryService {

    private final deliveryMapper delivery;

    public deliveryService(deliveryMapper delivery) {
        this.delivery = delivery;
    }

//    DAO에 만들어진 메소드 호출, 비지니스 로직이 필요하다면 작성
    public List<OrderProductDTO> deliveryDelivery() {
        return delivery.deliveryDelivery();
    }

    public List<NotDeliveryDTO> notDelivery(Map<String, Object> map){
        return delivery.notDelivery(map);
    }

    public int notDeliveryCount() { int notDelivery = delivery.notDeliveryCount();
        return notDelivery;
    }

}
