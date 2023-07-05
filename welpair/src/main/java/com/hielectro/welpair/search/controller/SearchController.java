package com.hielectro.welpair.search.controller;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.search.model.dto.SearchDTO;
import com.hielectro.welpair.search.model.service.SearchService;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellProductDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/consumer/")
public class SearchController {

    private final SearchService searchService;


    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 상품 검색 (ng) 1. 헤더 검색 기능
     * 1-1. 상품명 검색 시, 검색한 단어가 포함된 상품 리스트 출력
     */
    @GetMapping("search/search")
    public String searchResult(Model model, @RequestParam(value = "title", required = false) String title){
        System.out.println("------------- 상품검색 컨트롤러 1-1-1 in -------------");

        SearchDTO search = new SearchDTO();
        SellPageDTO sellPage = new SellPageDTO();

        System.out.println("search.getSellPage() = " + search.getSellPage());
        sellPage.setTitle(title);
        System.out.println("title = " + title);

        search.setSellPage(sellPage);
        System.out.println("search = " + search);

        List<SearchDTO> prodSearchList = searchService.searchResultByTitle(search);
        System.out.println("prodSearchList = " + prodSearchList);

        model.addAttribute("prodSearchList", prodSearchList);

        System.out.println("------------- 상품검색 컨트롤러 1-1-1 out -------------");
        return "consumer/search/search";
    }

    @PostMapping("search/search")
    @ResponseBody
    public List<SearchDTO> searchResult1(Model model, @RequestParam(value = "title", required = false) String title){
        System.out.println("------------- 상품검색 컨트롤러 1-1-2 in -------------");

        List<SearchDTO> prodSearchList = null;

        SearchDTO search = new SearchDTO();
        SellPageDTO sellPage = new SellPageDTO();

        System.out.println("search.getSellPage() = " + search.getSellPage());
        sellPage.setTitle(title);
        System.out.println("title = " + title);

        search.setSellPage(sellPage);
        System.out.println("search = " + search);

        prodSearchList = searchService.searchResultByTitle(search);
        System.out.println("prodSearchList = " + prodSearchList);

        System.out.println("------------- 상품검색 컨트롤러 1-1-2 out -------------");
        return prodSearchList;
    }
}
