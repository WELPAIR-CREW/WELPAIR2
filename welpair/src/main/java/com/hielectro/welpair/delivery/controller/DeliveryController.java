package com.hielectro.welpair.delivery.controller;

import com.hielectro.welpair.delivery.model.dto.DeliveryDTO;
import com.hielectro.welpair.delivery.model.dto.DriverDTO;
import com.hielectro.welpair.delivery.model.service.deliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    private final deliveryService service;

    public DeliveryController(deliveryService service) {
        this.service = service;
    }

    /* 배송 전체 현황 */
    @GetMapping("delivery_main")
    public String deliveryList(Model model) {
//      service의 메소드호출. 반환값 결과값에 따라 보여주는 View를 다르게 한다.
        List<DeliveryDTO> deliverylist = service.deliveryDelivery();
        System.out.println(deliverylist);
        model.addAttribute("deliveryList", deliverylist);
        return "admin/delivery/delivery_main";
    }

    /* 상품준비중 */
    @GetMapping("delivery_prepare")
    public String deliveryprepare() {
        List<DeliveryDTO> list = service.deliveryDelivery();


        return "admin/delivery/delivery_prepare";

    }

    /* 배송중 */
    @GetMapping("delivery_transit")
    public String deliverytransit() {

        return "admin/delivery/delivery_transit";

    }

    /* 배송완료 */
    @GetMapping("delivery_complete")
    public String deliverycomplete() {

        return "admin/delivery/delivery_complete";
    }
}
