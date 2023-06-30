package com.hielectro.welpair.search.model.service;

import com.hielectro.welpair.search.model.dao.SearchDAO;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchDAO searchDAO;

    public SearchServiceImpl(SearchDAO searchDAO) {
        this.searchDAO = searchDAO;
    }
}
