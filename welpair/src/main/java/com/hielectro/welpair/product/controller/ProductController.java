package com.hielectro.welpair.product.controller;

import com.hielectro.welpair.product.model.dto.SellProductDTO;
import com.hielectro.welpair.product.model.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"{id}"})
    public String defaultLocation(@PathVariable("id") String url) {
        return "admin/product/" + url;
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
