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

        System.out.println("============ 상품검색 서비스 1-1 in ============");

        List<SearchDTO> result = searchDAO.searchResultMain(search);

        System.out.println("============ 상품검색 서비스 1-1 out ============");
        return result;
    }

    /* 2-1 */
    @Override
    public List<SearchDTO> searchDetailResult(SearchDTO search) {
        System.out.println("============ 상품 상세 검색 서비스 2-1 in ============");

        List<SearchDTO> result = searchDAO.searchDetailResult(search);
        System.out.println("============ 상품 상세 검색 서비스 2-1 out ============");
        return result;
    }

    @Override
    public String searchTermsCategory(String categoryCode) {
        System.out.println("============ SearchTerms 서비스 99 in ============");

        String categoryName = searchDAO.searchTermsCategory(categoryCode);
        System.out.println("============ SearchTerms 서비스 99 out ============");
        return categoryName;
    }

    @Override
    public String searchTermsRefCategory(String refCategoryCode) {
        System.out.println("============ SearchTerms 서비스 99 in ============");

        String categoryName = searchDAO.searchTermsRefCategory(refCategoryCode);
        System.out.println("============ SearchTerms 서비스 99 out ============");
        return categoryName;
    }

}
