package com.hielectro.welpair.search.controller;

import com.hielectro.welpair.search.model.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/consumer/")
public class SearchController {

    private final SearchService searchService;


    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("search/search")
    public String searchResult(){
        System.out.println("------------- 상품검색 컨트롤러 1-1-1 in -------------");


        System.out.println("------------- 상품검색 컨트롤러 1-1-1 out -------------");
        return "consumer/search/search";
    }
}
