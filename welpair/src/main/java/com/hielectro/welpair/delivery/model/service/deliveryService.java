package com.hielectro.welpair.delivery.model.service;

import com.hielectro.welpair.delivery.model.dao.deliveryMapper;
import com.hielectro.welpair.delivery.model.dto.DeliveryDTO;
import com.hielectro.welpair.delivery.model.dto.DriverDTO;
import com.hielectro.welpair.delivery.model.dto.NotDeliveryDTO;
import com.hielectro.welpair.delivery.model.dto.OrderProductDTO;
import com.hielectro.welpair.member.model.dto.MemberDTO;
import com.hielectro.welpair.payment.model.dto.PointPayDTO;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<NotDeliveryDTO> notDelivery(){
        return delivery.notDelivery();
    }


}
