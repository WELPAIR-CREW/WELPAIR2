package com.hielectro.welpair.sellproduct.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;

@Controller
@RequestMapping("/sellproduct")
public class SellProductController {
    private final SellProductServiceImpl productService;
    private final int limit = 10;

    public SellProductController(SellProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping({"{id}"})
    public String defaultLocation(@PathVariable("id") String url) {
        return "admin/sellproduct/" + url;
    }

    @PostMapping(value = "list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SellProductDTO> productList(@RequestBody Map<String, String> request) {
        System.out.println("request : " + request);
        List<SellProductDTO> sellProductList = productService.selectProductList(request);
        System.out.println(sellProductList.size());
        return sellProductList;
    }

    @PostMapping(value = "count", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Integer> rowCount(@RequestBody(required = false) Map<String, String> request) {
        Map<String, Integer> response = new HashMap<>();
        int result = productService.sellProductSearchCount(request);
        int maxPageNo = (int) Math.ceil((double) result / 10);

        response.put("maxPageNo", maxPageNo);
        response.put("startPageNo", 1);
        response.put("endPageNo", 5);

        return response;
    }

    @PostMapping("delete")
    @ResponseBody
    public int delete(@RequestBody List<String> request) {
        System.out.println(request);
        try {
            return productService.delete(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
