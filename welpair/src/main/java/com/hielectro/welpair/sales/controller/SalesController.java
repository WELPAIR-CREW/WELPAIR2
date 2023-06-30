package com.hielectro.welpair.sales.controller;

import com.hielectro.welpair.sales.model.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/sales/")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("admin_sales")
    public String getSalesInfo(){
        System.out.println("-------------컨트롤러 1-1-1 in -------------");
        System.out.println("-------------컨트롤러 1-1-1 out -------------");

        return "admin/sales/admin_sales";
    }
}
