package com.hielectro.welpair.sales.controller;

import com.google.gson.Gson;
import com.hielectro.welpair.sales.model.dto.SalesDTO;
import com.hielectro.welpair.sales.model.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/sales/")
public class SalesController {

    private final SalesService salesService;


    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    /**
     * 매출관리 (ng)
     * 월별 총매출액 정보 전달
     * 막대그래프로 단순 표현 예정
     */
    @GetMapping("admin_sales")
    public String getMonthSales(Model model){
        System.out.println("------------- 매출 컨트롤러 1-2-1 in -------------");

//        List<SalesDTO> salesList = salesService.getMonthSales();
//        Gson gson = new Gson();
//        List<Map<String, Object>> monthlySales = new ArrayList<>();
//
//        for(SalesDTO sales : salesList){
//            Map<String, Object> monthlySale = new HashMap<>();
//            monthlySale.put("month", sales.getMonth() + "월");
//            monthlySale.put("totalSales", String.format("%,d원", sales.getTotalSales()));
//            monthlySales.add(monthlySale);
//            System.out.println("monthlySales = " + monthlySales);
//        }
//
//        String salesJson = gson.toJson(monthlySales);
//
//        model.addAttribute("salesJson", salesJson);
//        System.out.println("monthlySales = " + monthlySales);
//        System.out.println("salesJson = " + salesJson);

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
