package com.hielectro.welpair.post.model.service;

import com.hielectro.welpair.post.controller.BoardException;
import com.hielectro.welpair.post.controller.SelectCriteria;
import com.hielectro.welpair.post.model.dao.AdminBoardDAO;
import com.hielectro.welpair.post.model.dto.AdminBoardDTO;
import com.hielectro.welpair.post.model.dto.AdminBoardTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional
    public void MemberWriteSave(AdminBoardDTO adminBoardDTO) throws BoardException {

        int result = adminBoardDAO.insertPost(adminBoardDTO);

        if(!(result > 0)){
            throw new BoardException("게시글등록에 실패");
        }
    }



    @Override
    @Transactional
    public void MemberAskWriteSave(AdminBoardDTO adminBoardDTO) throws BoardException {

        int result = adminBoardDAO.insertPost(adminBoardDTO);

        if(!(result > 0)){
            throw new BoardException("게시글등록에 실패");
        }
    }




    public List<AdminBoardDTO> selectBoardList(SelectCriteria selectCriteria){

        List<AdminBoardDTO> adminBoardList = adminBoardDAO.selectBoardList(selectCriteria);

        return adminBoardList;
    }


    //////////////////  문의  ///////////////////////////
    public List<AdminBoardDTO> selectQnaList(SelectCriteria selectCriteria){

        List<AdminBoardDTO> adminQnaList = adminBoardDAO.selectQnaList(selectCriteria);

        return  adminQnaList;
    }

    public  List<AdminBoardDTO> selectAskList(SelectCriteria selectCriteria){
        List<AdminBoardDTO> adminAskList = adminBoardDAO.selectAskList(selectCriteria);
        return  adminAskList;
    }


    public List<AdminBoardDTO> selectNoticeList(SelectCriteria selectCriteria) {

        List<AdminBoardDTO> adminNoticeList = adminBoardDAO.selectNoticeList(selectCriteria);

        return  adminNoticeList;
    }

    public List<AdminBoardDTO> selectNoticeManagerList(SelectCriteria selectCriteria) {

        List<AdminBoardDTO> adminNoticeList = adminBoardDAO.selectNoticeManagerList(selectCriteria);

        return  adminNoticeList;
    }

    @Override
    @Transactional
    public AdminBoardDTO selectBoardDetail(String boardNo){

        AdminBoardDTO boardDetail = null;

        int result = adminBoardDAO.incrementBoardCount(boardNo);

        if(result > 0){
            boardDetail = adminBoardDAO.selectBoardDetail(boardNo);
        }

        return boardDetail;
    }

    public AdminBoardTypeDTO selectBoardType(String boardCate) {

        AdminBoardTypeDTO boardType = null;

        int result = adminBoardDAO.incrementBoardCount(boardCate);

        if(result > 0){
            boardType = adminBoardDAO.selectBoardType(boardCate);

        }
        return boardType;

    }






}
