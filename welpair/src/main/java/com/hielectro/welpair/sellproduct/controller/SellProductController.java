package com.hielectro.welpair.sellproduct.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDetailDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.service.SellProductServiceImpl;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("review")
    public String reviewLocation(Model model) {
        List<ReviewManagerDTO> list = productService.selectReviewList();
        model.addAttribute("list", list);
        return "admin/sellproduct/review";
    }

    @PostMapping(value = "list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SellProductDetailDTO> productList(@RequestBody Map<String, String> request) {
        System.out.println("request : " + request);
        List<SellProductDetailDTO> sellProductList = productService.selectProductList(request);
        System.out.println(sellProductList);
        return sellProductList;
    }

    @PostMapping(value = "count", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Integer> rowCount(@RequestBody(required = false) Map<String, String> request) {
        Map<String, Integer> response = new HashMap<>();
        int result = productService.sellProductSearchCount(request);
        System.out.println("result : " + result);
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
