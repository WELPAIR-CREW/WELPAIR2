package com.hielectro.welpair.search.model.service;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.dto.SearchDTO;
import com.hielectro.welpair.sellproduct.model.dto.ThumbnailImageDTO;

import java.util.List;

public interface SearchService {

    /* 1-1 */
    List<SearchDTO> searchResultMain(SearchDTO search);
   int searchCount(SearchDTO search);

    /* 2-1 */
    List<SearchDTO> searchDetailResult(SearchDTO search);

    List<ThumbnailImageDTO> searchResultThumb(String no) ;

    /* 99 */
    String searchTermsCategory(String categoryCode);
    String searchTermsRefCategory(String refCategoryCode);
}
