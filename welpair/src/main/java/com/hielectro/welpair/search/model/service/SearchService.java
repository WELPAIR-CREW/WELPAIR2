package com.hielectro.welpair.search.model.service;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.dto.SearchDTO;

import java.util.List;

public interface SearchService {

    /* 1-1 */
    List<SearchDTO> searchResultMain(SearchDTO search);

    /* 2-1 */
    List<SearchDTO> searchDetailResult(SearchDTO search);

    /* 99 */
    String searchTermsCategory(String categoryCode);
    String searchTermsRefCategory(String refCategoryCode);
}
