package com.hielectro.welpair.sales.controller;

import com.google.gson.Gson;
import com.hielectro.welpair.sales.model.dto.SalesDTO;
import com.hielectro.welpair.sales.model.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/sales/")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

//    @GetMapping("admin_sales")
//    public String getSalesInfo(Model model){
//        System.out.println("------------- 매출 컨트롤러 1-1-1 in -------------");
//        System.out.println("------------- 매출 컨트롤러 1-1-1 out -------------");
//
//        return "admin/sales/admin_sales";
//    }

    /**
     * 매출관리 (ng)
     * 월별 총매출액 정보 전달
     * 막대그래프로 단순 표현 예정
     */
    @GetMapping("admin_sales")
    public String getMonthSales(Model model){
        System.out.println("------------- 매출 컨트롤러 1-2-1 in -------------");

//        List<SalesDTO> monthlySales = salesService.getMonthSales();
//        model.addAttribute("monthlySales", monthlySales);

        List<SalesDTO> monthlySales = salesService.getMonthSales();
        Gson gson = new Gson();
        String salesJson = gson.toJson(monthlySales);

        model.addAttribute("salesJson", salesJson);
        System.out.println("monthlySales = " + monthlySales);
        System.out.println("salesJson = " + salesJson);
        System.out.println("------------- 매출 컨트롤러 1-2-1 out -------------");

        return "admin/sales/admin_sales";
    }
}
