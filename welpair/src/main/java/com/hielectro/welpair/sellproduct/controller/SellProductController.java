package com.hielectro.welpair.sellproduct.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;

@Controller
@RequestMapping("/sellproduct")
public class SellProductController {
    private final SellProductServiceImpl productService;

    public SellProductController(SellProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping({"{id}"})
    public String defaultLocation(@PathVariable("id") String url) {
        return "admin/sellproduct/" + url;
    }

    @GetMapping(value = "sellproductlist/{pageNo}", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SellProductDTO> getProductList(@PathVariable int pageNo) {
        List<SellProductDTO> sellProductList = productService.findSellProductByPageNo(pageNo);
        System.out.println(sellProductList);
        return sellProductList;
    }

    @PostMapping("totalcount")
    @ResponseBody
    public int getSellProductCount() {
        int result = productService.sellProductTotalCount();
        System.out.println(result);
        return result;
    }
}
