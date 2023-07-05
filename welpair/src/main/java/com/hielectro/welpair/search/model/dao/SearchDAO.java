package com.hielectro.welpair.search.model.dao;

import com.hielectro.welpair.common.Search;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchDAO {
    /* 1-1 */
    List<Search> searchResultByTitle();

}
