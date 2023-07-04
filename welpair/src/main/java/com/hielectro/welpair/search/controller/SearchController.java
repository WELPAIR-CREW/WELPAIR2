package com.hielectro.welpair.search.controller;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String searchResult(@ModelAttribute Search search){
        System.out.println("------------- 상품검색 컨트롤러 1-1-1 in -------------");

        String inputTitle = search.getTitle().toUpperCase();
        System.out.println("검색어 : " + inputTitle);


        List<Search> prodSearchList = searchService.searchResultByTitle();
        System.out.println("prodSearchList = " + prodSearchList);

        System.out.println("------------- 상품검색 컨트롤러 1-1-1 out -------------");
        return "consumer/search/search";
    }
}
