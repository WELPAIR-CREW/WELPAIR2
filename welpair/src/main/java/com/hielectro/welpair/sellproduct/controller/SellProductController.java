package com.hielectro.welpair.sellproduct.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hielectro.welpair.board.model.dto.QnAManagerDTO;
import com.hielectro.welpair.board.model.dto.ReviewManagerDTO;
import com.hielectro.welpair.common.Pagination;
import com.hielectro.welpair.common.Search;
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

    @GetMapping("{url}")
    public String defaultLocation(@PathVariable String url) {
        return "admin/sellproduct/" + url;
    }

    @GetMapping("review")
    public String reviewLocation(HttpServletRequest request, Model model,
                                 @RequestParam(required = false) String id, @RequestParam(required = false) String name,
                                 @RequestParam(required = false, defaultValue = "1") int currentPageNo) {
        String url = String.valueOf(request.getRequestURL());
        Map<String, Object> searchMap = new HashMap<>();
        Map<String, Integer> paging = null;
        searchMap.put("id", id);
        searchMap.put("name", name);

        if (!Pagination.getURL().equals(url)) {
            Pagination.init(url);
            int result = productService.reviewSearchCount(searchMap);
            paging = Pagination.paging(result, currentPageNo);
        } else {
            paging = Pagination.getParameter(currentPageNo);
        }

        model.addAttribute("paging", paging);
        searchMap.put("pageNo", currentPageNo);
        List<ReviewManagerDTO> list = productService.selectReviewList(searchMap);

        list.forEach(item -> {
            if (item.getContent().length() > 20) {
                String content = item.getContent();
                String subContent = content.substring(0, 20);

                item.setContent(subContent.concat("..."));
            }
        });


        model.addAttribute("list", list);
        return "admin/sellproduct/review";
    }

    @GetMapping("QnA")
    public String qnaLocation(HttpServletRequest request, Model model,
                              @RequestParam(required = false) String id, @RequestParam(required = false) String name,
                              @RequestParam(required = false, defaultValue = "1") int currentPageNo) {
        String url = String.valueOf(request.getRequestURL());
        Map<String, Object> searchMap = new HashMap<>();
        Map<String, Integer> paging = null;
        searchMap.put("id", id);
        searchMap.put("name", name);

        if (!Pagination.getURL().equals(url)) {
            Pagination.init(url);
            int result = productService.reviewSearchCount(searchMap);
            paging = Pagination.paging(result, currentPageNo);
        } else {
            paging = Pagination.getParameter(currentPageNo);
        }

        model.addAttribute("paging", paging);
        searchMap.put("pageNo", currentPageNo);
        List<QnAManagerDTO> list = productService.selectQnAList(searchMap);

        list.forEach(item -> {
            if (item.getContent().length() > 20) {
                String content = item.getContent();
                String subContent = content.substring(0, 20);

                item.setContent(subContent.concat("..."));
            }
        });


        model.addAttribute("list", list);
        return "admin/sellproduct/QnA";
    }

    @PostMapping(value = "sellProductListAPI", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SellProductDetailDTO> sellProductList(@RequestBody Map<String, String> request) {
        System.out.println("request : " + request);
        List<SellProductDetailDTO> sellProductList = productService.selectProductList(request);
        System.out.println(sellProductList);
        return sellProductList;
    }

    @PostMapping(value = "sellProductCountAPI", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Integer> sellProductCount(@RequestBody(required = false) Map<String, String> request) {
        int result = productService.sellProductSearchCount(request);
        Map<String, Integer> response = pagination(result);

        return response;
    }

    @PostMapping("sellProductDeleteAPI")
    @ResponseBody
    public int sellProductDelete(@RequestBody List<String> request) {
        System.out.println(request);
        try {
            return productService.sellProductDelete(request);
        } catch (Exception e) {
            throw new IllegalStateException("error");
        }
    }

    @GetMapping("test")
    public String testMethod(@ModelAttribute Search search) {
        System.out.println("test : " + search);
        return "redirect:/sellproduct/review";
    }
    public Map<String, Integer> pagination(int length) {
        Map<String, Integer> response = new HashMap<>();
        int maxPageNo = (int) Math.ceil((double) length / limit);

        response.put("maxPageNo", maxPageNo);
        response.put("startPageNo", 1);
        response.put("endPageNo", 5);

        return response;
    }
}
