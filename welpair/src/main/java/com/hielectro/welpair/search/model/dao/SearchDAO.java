package com.hielectro.welpair.search.model.dao;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchDAO {
    /* 1-1 */
    List<SearchDTO> searchResultMain(SearchDTO search);
    int searchCount(SearchDTO search);

    /* 2-1 */
    List<SearchDTO> searchDetailResult(SearchDTO search);

    /* 99 */

    String searchTermsCategory(String categoryCode);
    String searchTermsRefCategory(String refCategoryCode);


}
