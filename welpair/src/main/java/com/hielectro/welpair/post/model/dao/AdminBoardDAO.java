package com.hielectro.welpair.post.model.dao;

import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import com.hielectro.welpair.post.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminBoardDAO {


    int insertPost(AdminBoardDTO adminBoardDTO);

    MemberDTO findByMemberId(String empNo); // 사번 찾기

}
