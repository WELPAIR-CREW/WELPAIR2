package com.hielectro.welpair.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    /* 배송 전체 현황 */
    @GetMapping("delivery_main")
    public String deliveryMain() {

        return "admin/delivery/delivery_main";

    }

    /* 상품준비중 */
    @GetMapping("delivery_prepare")
    public String deliveryprepare() {

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
