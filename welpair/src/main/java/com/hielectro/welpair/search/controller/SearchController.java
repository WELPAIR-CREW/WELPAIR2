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

import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/search/")
public class SearchController {

    private final SearchService searchService;


    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * 상품 검색 (ng) 1. 헤더 검색 기능
     * 1-1. 상품명 검색 시, 검색한 단어가 포함된 상품 리스트 출력
     */
    @GetMapping("search")
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

        return "/consumer/search/search";
    }

    @PostMapping("detail")
    @ResponseBody
    public String searchDetailResult(Model model, @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String categoryCode,
                                              @RequestParam(required = false) Integer minPrice,
                                              @RequestParam(required = false) Integer maxPrice) {
        System.out.println("------------- 상품 상세 검색 컨트롤러 1-1-2 in -------------");

        SearchDTO search = new SearchDTO();
        SellPageDTO sellPage = new SellPageDTO();
        ProductDTO product = new ProductDTO();

        sellPage.setTitle(title);
        product.setCategoryCode(categoryCode);

        search.setSellPage(sellPage);
        search.setProduct(product);
        search.setMinPrice(minPrice);
        search.setMaxPrice(maxPrice);

        List<SearchDTO> prodSearchList = null;
        if(search != null){
            prodSearchList = searchService.searchDetailResult(search);

            System.out.println("prodSearchList = " + prodSearchList);

            model.addAttribute("prodSearchList", prodSearchList);

        } else{
            model.addAttribute("prodSearchList", Collections.emptyList());
        }

        if(prodSearchList.isEmpty()){
            model.addAttribute("noResultMessage", "검색한 결과가 없습니다.");
        }

        System.out.println("------------- 상품 상세 검색 컨트롤러 1-1-2 out -------------");
        return "search/search";
//        return prodSearchList;
    }

}
