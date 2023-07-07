package com.hielectro.welpair.search.controller;

import com.hielectro.welpair.inventory.model.dto.CategoryDTO;
import com.hielectro.welpair.inventory.model.dto.ProductDTO;
import com.hielectro.welpair.search.model.dto.SearchDTO;
import com.hielectro.welpair.search.model.service.SearchService;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Comparator;
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
    public String searchResultMain(Model model, @RequestParam(value = "title", required = false) String title
                                            ,@RequestParam(value = "categoryCode", required = false) String categoryCode
                                            ,@RequestParam(value = "refCategoryCode", required = false) String refCategoryCode
                                            ,@RequestParam(value = "productStatus", required = false) String productStatus){
        System.out.println("------------- 상품검색 컨트롤러 1-1 in -------------");

        SearchDTO search = new SearchDTO();
        SellPageDTO sellPage = new SellPageDTO();
        ProductDTO product = new ProductDTO();
        CategoryDTO category = new CategoryDTO();

        System.out.println("title = " + title);
        System.out.println("categoryCode = " + categoryCode);
        System.out.println("refCategoryCode = " + refCategoryCode);
        System.out.println("productStatus = " + productStatus);

        sellPage.setTitle(title);
        product.setCategoryCode(categoryCode);
        product.setProductStatus(productStatus);
        category.setRefCategoryCode(refCategoryCode);

        search.setSellPage(sellPage);
        search.setCategory(category);
        search.setProduct(product);

        System.out.println("sellPage = " + sellPage);
        System.out.println("category = " + category);
        System.out.println("product = " + product);
        System.out.println("search = " + search);

        List<SearchDTO> prodSearchList = searchService.searchResultMain(search);

        if(search != null){
            System.out.println("prodSearchList = " + prodSearchList);
            model.addAttribute("prodSearchList", prodSearchList);
        } else{
            model.addAttribute("prodSearchList", Collections.emptyList());
        }

        if(prodSearchList.size() < 1){
            model.addAttribute("noResultMessage", "검색한 결과가 없습니다.");
        }

        model.addAttribute("searchTerms", createSearchTerms(title, categoryCode, refCategoryCode, productStatus));
        System.out.println("------------- 상품검색 컨트롤러 1-1 out -------------");
        return "consumer/search/search";
    }

    /**
     * 상품 검색 (ng) 2. 검색 기능
     * 2-1. 검색 결과 페이지에서 추가 검색
     */
    @PostMapping("detail")
    @ResponseBody
    public List<SearchDTO> searchDetailResult(Model model, SearchDTO search) {
        System.out.println("------------- 상품 상세 검색 컨트롤러 2-1 in -------------");

        System.out.println(search);

        SellPageDTO sellPage = search.getSellPage();
        ProductDTO product = search.getProduct();
        CategoryDTO category = search.getCategory();

        List<SearchDTO> prodSearchList = null;
        if(search != null){
            prodSearchList = searchService.searchDetailResult(search);
            System.out.println("prodSearchList = " + prodSearchList);
            model.addAttribute("prodSearchList", prodSearchList);


        } else{
            model.addAttribute("prodSearchList", Collections.emptyList());
        }

        if(prodSearchList.size() < 1){
            model.addAttribute("noResultMessage", "검색한 결과가 없습니다.");
        }

        model.addAttribute("searchTerms", createSearchTerms(sellPage.getTitle(), product.getCategoryCode(),
                category.getRefCategoryCode(), product.getProductStatus()));
        System.out.println("------------- 상품 상세 검색 컨트롤러 2-2 out -------------");
        return prodSearchList;
    }


    private String createSearchTerms(String title, String categoryCode, String refCategoryCode, String productStatus) {
        StringBuilder searchTerms = new StringBuilder();

        if (title != null && !title.isEmpty()) {
            searchTerms.append("<검색어> ").append(title).append(", ");
        }
        if (productStatus != null && !productStatus.isEmpty()) {
            searchTerms.append("<카테고리> ").append(productStatus).append(", ");
        }

        if (categoryCode != null && !categoryCode.isEmpty()){
            String categoryName = null;
            categoryName = searchService.searchTermsCategory(categoryCode);
            searchTerms.append("<카테고리> ").append(categoryName).append(", ");
        }
        if (refCategoryCode != null && !refCategoryCode.isEmpty()){
            String categoryName = null;
            categoryName = searchService.searchTermsRefCategory(refCategoryCode);
            searchTerms.append("<카테고리> ").append(categoryName).append(", ");
        }


        if (searchTerms.length() > 2) {
            searchTerms.setLength(searchTerms.length() - 2);
        }

        return searchTerms.toString();
    }
}
