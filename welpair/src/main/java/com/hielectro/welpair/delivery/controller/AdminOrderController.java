package com.hielectro.welpair.delivery.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class AdminOrderController {

    /* 고객별 주문 내역 */
    @GetMapping("order_list")
    @PreAuthorize("hasRole('ADMIN')")
    public String orderlist() {

        return "admin/order/order_list";

    }

    /* 주문관리 */
    @GetMapping("order_manage")
    @PreAuthorize("hasRole('ADMIN')")
    public String ordermanage() {

        return "admin/order/order_manage";

    }
}
