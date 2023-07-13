package com.hielectro.welpair.post.model.dao;

import com.hielectro.welpair.post.controller.SelectCriteria;
import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import com.hielectro.welpair.post.model.dto.AdminBoardTypeDTO;
import com.hielectro.welpair.post.model.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminBoardDAO {


    int insertPost(AdminBoardDTO adminBoardDTO);

    MemberDTO findByMemberId(String empNo); // 사번 찾기

    int selectTotalCount();

    List<AdminBoardDTO> selectBoardList(SelectCriteria selectCriteria);


    List<AdminBoardDTO> selectQnaList(SelectCriteria selectCriteria);

    List<AdminBoardDTO> selectNoticeList(SelectCriteria selectCriteria);

    int incrementBoardCount(String boardNo);

    AdminBoardDTO selectBoardDetail(String boardNo);

    AdminBoardTypeDTO selectBoardType(String boardCate);


}
