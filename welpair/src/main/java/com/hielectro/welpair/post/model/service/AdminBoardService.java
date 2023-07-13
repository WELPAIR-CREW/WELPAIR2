package com.hielectro.welpair.post.model.service;

import com.hielectro.welpair.post.controller.BoardException;
import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import org.springframework.transaction.annotation.Transactional;


public interface AdminBoardService {


    public void PostSave(AdminBoardDTO adminBoardDTO) throws BoardException;

    int selectTotalCount();

    public void MemberWriteSave(AdminBoardDTO adminBoardDTO) throws  BoardException;

    public void MemberAskWriteSave(AdminBoardDTO adminBoardDTO) throws  BoardException;


    @Transactional
    AdminBoardDTO selectBoardDetail(String boardNo);

}
