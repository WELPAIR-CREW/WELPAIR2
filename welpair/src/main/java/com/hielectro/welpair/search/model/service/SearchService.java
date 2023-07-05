package com.hielectro.welpair.search.model.service;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.dto.SearchDTO;

import java.util.List;

public interface SearchService {

    /* 1-1 */
    List<SearchDTO> searchResultByTitle(SearchDTO search);
}
