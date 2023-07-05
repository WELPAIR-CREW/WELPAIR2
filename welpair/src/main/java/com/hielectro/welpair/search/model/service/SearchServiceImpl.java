package com.hielectro.welpair.search.model.service;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.dao.SearchDAO;
import com.hielectro.welpair.search.model.dto.SearchDTO;
import com.hielectro.welpair.sellproduct.model.dto.SellPageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchDAO searchDAO;

    public SearchServiceImpl(SearchDAO searchDAO) {
        this.searchDAO = searchDAO;
    }

    /* 1-1 */
    @Override
    public List<SearchDTO> searchResultMain(SearchDTO search) {

        System.out.println("============ 상품검색 서비스 1-1-1 in ============");

        String title = search.getSellPage().getTitle();
        String categoryCode = search.getProduct().getCategoryCode();
        System.out.println("title = " + title);
        System.out.println("categoryCode = " + categoryCode);

        List<SearchDTO> result = searchDAO.searchResultMain(title, categoryCode);

        System.out.println("============ 상품검색 서비스 1-1-1 out ============");
        return result;
    }

    @Override
    public List<SearchDTO> searchDetailResult(SearchDTO search) {
        System.out.println("============ 상품 상세 검색 서비스 1-1-2 in ============");


        List<SearchDTO> result = searchDAO.searchDetailResult(search);
        System.out.println("============ 상품 상세 검색 서비스 1-1-2 out ============");
        return result;
    }
}
