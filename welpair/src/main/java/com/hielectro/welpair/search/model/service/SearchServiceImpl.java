package com.hielectro.welpair.search.model.service;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.dao.SearchDAO;
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
    public List<Search> searchResultByTitle() {
        return searchDAO.searchResultByTitle();
    }
}
