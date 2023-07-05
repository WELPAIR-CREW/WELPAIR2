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
    public List<SearchDTO> searchResultByTitle(SearchDTO search) {

        System.out.println("============ 상품검색 서비스 1-1-1 in ============");

        SellPageDTO sellPage = new SellPageDTO();
        String title = search.getSellPage().getTitle();
        System.out.println("title = " + title);

        List<SearchDTO> result = searchDAO.searchResultByTitle(title);

        System.out.println("============ 상품검색 서비스 1-1-1 out ============");
        return result;
    }
}
