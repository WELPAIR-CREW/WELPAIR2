package com.hielectro.welpair.post.model.service;

import com.hielectro.welpair.post.controller.BoardException;
import com.hielectro.welpair.post.controller.SelectCriteria;
import com.hielectro.welpair.post.model.dao.AdminBoardDAO;
import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AdminBoardServiceImpl implements AdminBoardService{


    public final AdminBoardDAO adminBoardDAO;


    @Autowired
    public AdminBoardServiceImpl(AdminBoardDAO adminBoardDAO) {
        this.adminBoardDAO = adminBoardDAO;
    }

    @Override
    @Transactional
    public void PostSave(AdminBoardDTO adminBoardDTO) throws BoardException {

        int result = adminBoardDAO.insertPost(adminBoardDTO);

        if(!(result > 0)){
            throw new BoardException("게시글등록에 실패");
        }

    }

    ////////////////////   자주묻는 질문 리스트    ///////////////////
    public int selectTotalCount() {
        return adminBoardDAO.selectTotalCount();
    }


    public List<AdminBoardDTO> selectBoardList(SelectCriteria selectCriteria){

        List<AdminBoardDTO> adminBoardList = adminBoardDAO.selectBoardList(selectCriteria);

        return adminBoardList;
    }









}
