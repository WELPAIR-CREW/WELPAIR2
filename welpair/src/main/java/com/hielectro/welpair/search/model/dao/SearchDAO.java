package com.hielectro.welpair.search.model.dao;

import com.hielectro.welpair.common.Search;
import com.hielectro.welpair.search.model.dto.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchDAO {
    /* 1-1 */
//    List<SearchDTO> searchResultByTitle(String title);
    List<SearchDTO> searchResultMain(String title, String categoryCode);

    List<SearchDTO> searchDetailResult(SearchDTO search);
}
